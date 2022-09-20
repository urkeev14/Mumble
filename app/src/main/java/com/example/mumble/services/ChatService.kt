package com.example.mumble.services

import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.services.manager.UserAnnouncementManager
import com.example.mumble.services.manager.UserDiscoveryManager
import com.example.mumble.services.manager.chat.impl.ChatManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * TODO See how and when to unregister current user
 *  tip: can not unregister it on service destroy, since
 *  that can happen during recomposition (phone rotation)
 *
 */
@AndroidEntryPoint
class ChatService : LifecycleService() {

    @Inject
    lateinit var chatManager: ChatManager

    @Inject
    lateinit var userDiscoveryManager: UserDiscoveryManager

    @Inject
    lateinit var userAnnouncementManager: UserAnnouncementManager

    @Inject
    lateinit var readCurrentUserUseCase: ReadCurrentUserUseCase

    override fun onCreate() {
        super.onCreate()
        chatManager.start()
        userDiscoveryManager.start()
        userAnnouncementManager.start(this@ChatService)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        userDiscoveryManager.stop()
        userAnnouncementManager.stop()
        stopSelf()
    }

    companion object {
        const val SERVICE_TYPE = "_mumble._tcp."
        const val ATTRIBUTE_KEY_USER_ID = "id"
        const val CHANNEL_ID = "MUMBLE"
    }
}
