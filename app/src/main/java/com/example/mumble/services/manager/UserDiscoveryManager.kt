package com.example.mumble.services.manager

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.usecase.CreateUserUseCase
import com.example.mumble.domain.usecase.DeleteUserUseCase
import com.example.mumble.domain.usecase.ReadAllUsersOnlineUseCase
import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.domain.usecase.UpdateCurrentUserUseCase
import com.example.mumble.services.ChatService
import com.example.mumble.services.ChatService.Companion.ATTRIBUTE_KEY_USER_ID
import com.example.mumble.utils.extensions.isFromMumbleApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class UserDiscoveryManager @Inject constructor(
    val nsdManager: NsdManager,
    private val createUserUseCase: CreateUserUseCase,
    private val readCurrentUserUseCase: ReadCurrentUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateCurrentUserUseCase: UpdateCurrentUserUseCase,
    private val readAllUsersOnlineUseCase: ReadAllUsersOnlineUseCase
) : IUserDiscoveryManager {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var discoveryListener: NsdManager.DiscoveryListener? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var resolveListener: NsdManager.ResolveListener? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var isResolveListenerBusy = AtomicBoolean(false)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var pendingNsdServices = ConcurrentLinkedQueue<NsdServiceInfo>()

    override fun start() {
        initializeResolveListener()
        discoverServices()
    }

    private fun discoverServices() {
        stop()
        initializeDiscoveryListener()

        nsdManager.discoverServices(
            ChatService.SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener
        )
    }

    private fun initializeDiscoveryListener() {

        discoveryListener = object : NsdManager.DiscoveryListener {

            override fun onDiscoveryStarted(regType: String) {
                // do nothing
            }

            override fun onServiceFound(service: NsdServiceInfo) {
                Log.d(TAG, "onServiceFound: ${service.serviceName}")

                if (service.isFromMumbleApp()) {
                    if (isResolveListenerBusy.compareAndSet(false, true)) {
                        nsdManager.resolveService(service, resolveListener)
                    } else {
                        pendingNsdServices.add(service)
                    }
                }
            }

            override fun onServiceLost(service: NsdServiceInfo) {
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d(TAG, "onServiceLost: ${service.serviceName}")
                    if (service.isFromMumbleApp().not()) return@launch

                    val iterator = pendingNsdServices.iterator()
                    while (iterator.hasNext()) {
                        if (iterator.next().serviceName == service.serviceName) {
                            iterator.remove()
                            return@launch
                        }
                    }
                    deleteUserIfFound(service)
                }
            }

            override fun onDiscoveryStopped(serviceType: String) {
                // do nothing
            }

            override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
                stop()
            }

            override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
                nsdManager.stopServiceDiscovery(this)
            }
        }
    }

    private suspend fun deleteUserIfFound(service: NsdServiceInfo) {
        val nickname = service.serviceName

        val foundUser = readAllUsersOnlineUseCase().first().firstOrNull {
            it.username == nickname
        } ?: return

        deleteUserUseCase(foundUser.id)
    }

    private fun initializeResolveListener() {
        resolveListener = object : NsdManager.ResolveListener {
            override fun onServiceResolved(service: NsdServiceInfo) {
                Log.d(TAG, "onServiceResolved: " + service.serviceName)
                handleResolved(service)
                resolveNextServiceInQueue()
            }

            override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                Log.d(TAG, "onResolveFailed: " + serviceInfo.serviceName)
                resolveNextServiceInQueue()
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun handleResolved(service: NsdServiceInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentUser = readCurrentUserUseCase().first()
            if (foundNewUser(service, currentUser)) {
                createNewUser(service)
            } else if (currentUser.isOnline.not()) {
                updateCurrentUser(service)
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun foundNewUser(
        serviceInfo: NsdServiceInfo,
        currentUserEntity: UserEntity
    ) = serviceInfo.serviceName != currentUserEntity.username

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    suspend fun createNewUser(serviceInfo: NsdServiceInfo) {
        val nickname: String = serviceInfo.serviceName
        try {
            val port: Int = serviceInfo.port
            val host: String = serviceInfo.host.hostName
            val id = String(serviceInfo.attributes[ATTRIBUTE_KEY_USER_ID] ?: byteArrayOf())
            val uuid = UUID.fromString(id)

            createUserUseCase.invoke(
                UserEntity(
                    id = uuid,
                    username = nickname,
                    host = host,
                    port = port,
                    isOnline = true
                )
            )
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "createNewUser: Could not create user $nickname" + e.message)
        }
    }

    private suspend fun updateCurrentUser(serviceInfo: NsdServiceInfo) {
        val nickname: String = serviceInfo.serviceName
        updateCurrentUserUseCase(UserEntity(username = nickname, isOnline = true))
    }

    override fun stop() {
        if (discoveryListener != null) {
            try {
                nsdManager.stopServiceDiscovery(discoveryListener)
            } finally {
                discoveryListener = null
            }
        }
    }

    // Resolve next NSD service pending resolution
    private fun resolveNextServiceInQueue() {
        // Get the next NSD service waiting to be resolved from the queue
        val nextNsdService = pendingNsdServices.poll()
        if (nextNsdService != null) {
            // There was one. Send to be resolved.
            nsdManager.resolveService(nextNsdService, resolveListener)
        } else {
            // There was no pending service. Release the flag
            isResolveListenerBusy.set(false)
        }
    }

    companion object {
        const val TAG = "UserDiscoveryManager"
    }
}
