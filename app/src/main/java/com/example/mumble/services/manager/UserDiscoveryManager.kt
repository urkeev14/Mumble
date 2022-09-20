package com.example.mumble.services.manager

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.mumble.domain.model.User
import com.example.mumble.domain.usecase.CreateUserUseCase
import com.example.mumble.domain.usecase.DeleteUserUseCase
import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.domain.usecase.SetUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateCurrentUserUseCase
import com.example.mumble.services.ChatService
import com.example.mumble.utils.extensions.isFromMumbleApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDiscoveryManager @Inject constructor(
    private val nsdManager: NsdManager,
    private val createUserUseCase: CreateUserUseCase,
    private val readCurrentUserUseCase: ReadCurrentUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateCurrentUserUseCase: UpdateCurrentUserUseCase,
    private val setUiMessageUseCase: SetUiMessageUseCase
) {

    private val discoveryListener = object : NsdManager.DiscoveryListener {

        override fun onDiscoveryStarted(regType: String) {}

        override fun onServiceFound(service: NsdServiceInfo) {
            if (service.isFromMumbleApp().not()) return

            nsdManager.resolveService(service, resolveListener())
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            if (service.isFromMumbleApp().not()) return

            val nickname = service.serviceName
            deleteUserUseCase(nickname)
        }

        override fun onDiscoveryStopped(serviceType: String) {}

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            nsdManager.stopServiceDiscovery(this)
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            nsdManager.stopServiceDiscovery(this)
        }
    }

    fun resolveListener() = object : NsdManager.ResolveListener {

        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
        }

        // TODO: Check if current users is announced online
        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            scope.launch {
                val currentUser = readCurrentUserUseCase().first()
                if (foundNewUser(serviceInfo, currentUser)) {
                    createNewUser(serviceInfo)
                    return@launch
                }
                if (currentUser.isOnline.not()) {
                    updateCurrentUser(serviceInfo)
                }
                Log.d(TAG, "You announced the chat !!!")
            }
        }
    }

    private fun foundNewUser(
        serviceInfo: NsdServiceInfo,
        currentUser: User
    ) = serviceInfo.serviceName != currentUser.username

    private lateinit var scope: LifecycleCoroutineScope

    fun discoverChats(lifecycleOwner: LifecycleOwner) {
        scope = lifecycleOwner.lifecycleScope
        nsdManager.discoverServices(
            ChatService.SERVICE_TYPE,
            NsdManager.PROTOCOL_DNS_SD,
            discoveryListener
        )
    }

    private fun createNewUser(serviceInfo: NsdServiceInfo) {
        scope.launch(Dispatchers.IO) {
            val port: Int = serviceInfo.port
            val host: String = serviceInfo.host.hostName
            val nickname: String = serviceInfo.serviceName
            createUserUseCase(
                User(
                    username = nickname,
                    host = host,
                    port = port,
                    isOnline = true
                )
            )
        }
    }

    private fun updateCurrentUser(serviceInfo: NsdServiceInfo) {
        scope.launch(Dispatchers.IO) {
            val port: Int = serviceInfo.port
            val host: String = serviceInfo.host.hostName
            val nickname: String = serviceInfo.serviceName
            updateCurrentUserUseCase(
                User(
                    username = nickname,
                    host = host,
                    port = port,
                    isOnline = true
                )
            )
        }
    }

    companion object {
        const val TAG = "UserDiscoveryManager"
    }
}
