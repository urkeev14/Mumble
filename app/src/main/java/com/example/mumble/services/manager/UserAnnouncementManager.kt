package com.example.mumble.services.manager

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mumble.R
import com.example.mumble.domain.model.User
import com.example.mumble.domain.usecase.DeleteUserUseCase
import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.domain.usecase.SetUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateCurrentUserUseCase
import com.example.mumble.services.ChatService
import com.example.mumble.utils.UiMessage
import com.example.mumble.utils.extensions.isFromMumbleApp
import com.example.mumble.utils.getAvailablePort
import com.example.mumble.utils.getIPv4Address
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress
import javax.inject.Inject

class UserAnnouncementManager @Inject constructor(
    private val nsdManager: NsdManager,
    private val readCurrentUserUseCase: ReadCurrentUserUseCase,
    private val setUiMessageUseCase: SetUiMessageUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateCurrentUserUseCase: UpdateCurrentUserUseCase
) {

    private lateinit var lifecycleOwner: LifecycleOwner
    private val registrationListener = object : NsdManager.RegistrationListener {

        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
            setUiMessage(R.string.chat_registered, false)
        }

        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            setUiMessage(R.string.chat_registration_failed, true)
        }

        override fun onServiceUnregistered(arg0: NsdServiceInfo) {
            lifecycleOwner.lifecycleScope.launch {
                if (arg0.isFromMumbleApp().not()) return@launch

                val userUnregisteredUsername = arg0.serviceName
                val currentUser = readCurrentUserUseCase().first()

                if (userUnregisteredUsername == currentUser.username) {
                    updateCurrentUserUseCase(User())
                    return@launch
                }

                deleteUserUseCase(userUnregisteredUsername)
            }
        }

        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
        }
    }

    fun observeChatAvailableAnnouncement(owner: LifecycleOwner) {
        this.lifecycleOwner = owner

        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                readCurrentUserUseCase().collect { user ->
                    if (user.username.isEmpty() || user.isOnline) return@collect

                    announceUserReadyToChat(user.username)
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

        nsdManager.registerService(
            getServiceInfo(userNickname, ipAddress),
            NsdManager.PROTOCOL_DNS_SD,
            registrationListener
        )
    }

    private suspend fun getServiceInfo(userNickname: String, ipAddress: String?) =
        withContext(Dispatchers.IO) {
            NsdServiceInfo().apply {
                serviceName = userNickname
                serviceType = ChatService.SERVICE_TYPE
                port = getAvailablePort()
                host = InetAddress.getByName(ipAddress)
            }
        }

    private fun setUiMessage(stringMessageResId: Int, isError: Boolean) {
        lifecycleOwner.lifecycleScope.launch {
            setUiMessageUseCase(
                UiMessage.StringResource(
                    resId = stringMessageResId,
                    isError = isError
                )
            )
        }
    }

    /**
     * Research the usage
     *
     *
     fun unregisterCurrentUser() {
     nsdManager.unregisterService(registrationListener)
     }
     */

    companion object {
        const val TAG = "UserAnnouncementService"
    }
}
