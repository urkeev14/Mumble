package com.example.mumble.services

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mumble.R
import com.example.mumble.domain.model.User
import com.example.mumble.domain.usecase.CreateUserUseCase
import com.example.mumble.domain.usecase.DeleteUserUseCase
import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.domain.usecase.SetUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateCurrentUserUseCase
import com.example.mumble.utils.UiMessage
import com.example.mumble.utils.getAvailablePort
import com.example.mumble.utils.getIPv4Address
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress
import javax.inject.Inject

@AndroidEntryPoint
class ChatAnnouncementService : LifecycleService() {

    private val TAG = "ChatService"

    private lateinit var currentUser: User

    @Inject
    lateinit var createUserUseCase: CreateUserUseCase

    @Inject
    lateinit var deleteUserUseCase: DeleteUserUseCase

    @Inject
    lateinit var readCurrentUserUseCase: ReadCurrentUserUseCase

    @Inject
    lateinit var updateCurrentUserUseCase: UpdateCurrentUserUseCase

    @Inject
    lateinit var setUiMessageUseCase: SetUiMessageUseCase

    private lateinit var nsdManager: NsdManager

    private val registrationListener = object : NsdManager.RegistrationListener {

        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
            Log.d(TAG, "onServiceRegistered: ")
            setUiMessage(R.string.chat_registered, false)
        }

        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            Log.d(TAG, "onRegistrationFailed: ")
            setUiMessage(R.string.chat_registration_failed, true)
        }

        override fun onServiceUnregistered(arg0: NsdServiceInfo) {
            if (arg0.isFromMumbleApp()) {
                if (arg0.serviceName == currentUser.username) {
                    lifecycleScope.launch {
                        updateCurrentUserUseCase(User())
                    }
                } else {
                    val nickname = arg0.serviceName
                    deleteUserUseCase(nickname)
                }
            }
        }

        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
        }
    }

    fun resolveListener() = object : NsdManager.ResolveListener {

        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            Log.d(TAG, "onResolveFailed: ")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            if (serviceInfo.serviceName == currentUser.username) {
                if (currentUser.isOnline.not()) {
                    updateCurrentUser(serviceInfo)
                }
                Log.d(TAG, "You announced the chat !!!")
                return
            }
            createNewUser(serviceInfo)
        }
    }

    private val discoveryListener = object : NsdManager.DiscoveryListener {

        override fun onDiscoveryStarted(regType: String) {}

        override fun onServiceFound(service: NsdServiceInfo) {
            if (service.isFromMumbleApp()) {
                nsdManager.resolveService(service, resolveListener())
            }
        }

        override fun onServiceLost(service: NsdServiceInfo) {}

        override fun onDiscoveryStopped(serviceType: String) {}

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            nsdManager.stopServiceDiscovery(this)
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            nsdManager.stopServiceDiscovery(this)
        }
    }

    private fun createNewUser(serviceInfo: NsdServiceInfo) {
        lifecycleScope.launch {
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
        val port: Int = serviceInfo.port
        val host: String = serviceInfo.host.hostName
        val nickname: String = serviceInfo.serviceName
        lifecycleScope.launch {
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

    override fun onCreate() {
        super.onCreate()
        nsdManager = (getSystemService(Context.NSD_SERVICE) as NsdManager)
        discoverChats()
        announceChat()
    }

    private fun discoverChats() {
        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
    }

    private fun NsdServiceInfo.isFromMumbleApp(): Boolean {
        return serviceType.contains(SERVICE_TYPE)
    }

    private fun announceChat() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                readCurrentUserUseCase().collect { user ->
                    if (user.username.isNotEmpty() && user.isOnline.not()) {
                        announceUserReadyToChat(user.username)
                    }
                }
            }
        }
    }

    private suspend fun announceUserReadyToChat(userNickname: String) {
        val ipAddress = withContext(Dispatchers.IO) {
            getIPv4Address()
        }
        if (ipAddress.isNullOrEmpty()) {
            setUiMessage(R.string.ip_address_unavailable, true)
            return
        }

        val serviceInfo = withContext(Dispatchers.IO) {
            NsdServiceInfo().apply {
                serviceName = userNickname
                serviceType = SERVICE_TYPE
                port = getAvailablePort()
                host = InetAddress.getByName(ipAddress)
            }
        }

        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
    }

    private fun setUiMessage(stringMessageResId: Int, isError: Boolean) {
        lifecycleScope.launch {
            setUiMessageUseCase(
                UiMessage.StringResource(
                    resId = stringMessageResId,
                    isError = isError
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        nsdManager.unregisterService(registrationListener)
    }

    companion object {
        const val SERVICE_TYPE = "_mumble._tcp"
    }
}
