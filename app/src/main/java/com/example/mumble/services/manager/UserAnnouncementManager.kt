package com.example.mumble.services.manager

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mumble.R
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.usecase.DeleteUserUseCase
import com.example.mumble.domain.usecase.ReadAllUsersOnlineUseCase
import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.domain.usecase.SetUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateCurrentUserUseCase
import com.example.mumble.services.ChatService
import com.example.mumble.services.ChatService.Companion.ATTRIBUTE_KEY_USER_ID
import com.example.mumble.utils.UiMessage
import com.example.mumble.utils.extensions.isFromMumbleApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.util.UUID
import javax.inject.Inject

class UserAnnouncementManager @Inject constructor(
    private val nsdManager: NsdManager,
    private val readCurrentUserUseCase: ReadCurrentUserUseCase,
    private val setUiMessageUseCase: SetUiMessageUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateCurrentUserUseCase: UpdateCurrentUserUseCase,
    private val readAllUsersOnlineUseCase: ReadAllUsersOnlineUseCase
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
                    updateCurrentUserUseCase(UserEntity())
                    return@launch
                }

                val foundUser = readAllUsersOnlineUseCase().first().firstOrNull {
                    it.username == userUnregisteredUsername
                } ?: return@launch

                deleteUserUseCase(foundUser.id)
            }
        }

        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // do nothing
        }
    }

    fun start(owner: LifecycleOwner) {
        this.lifecycleOwner = owner

        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                readCurrentUserUseCase().collect { user ->
                    if (user.isReadyToChat.not()) return@collect

                    announceUserReadyToChat(user)
                }
            }
        }
    }

    private suspend fun announceUserReadyToChat(userEntity: UserEntity) {
        nsdManager.registerService(
            getServiceInfo(userEntity),
            NsdManager.PROTOCOL_DNS_SD,
            registrationListener
        )
    }

    private suspend fun getServiceInfo(userEntity: UserEntity) =
        withContext(Dispatchers.IO) {
            NsdServiceInfo().apply {
                serviceName = userEntity.username
                serviceType = ChatService.SERVICE_TYPE
                port = userEntity.port
                host = InetAddress.getByName(userEntity.host)
                setAttribute(ATTRIBUTE_KEY_USER_ID, UUID.randomUUID().toString())
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

    fun stop() {
        try {
            nsdManager.unregisterService(registrationListener)
        } catch (e: Exception) {
            Log.d(TAG, "stop: ${e.message}")
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
