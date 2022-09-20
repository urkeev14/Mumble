package com.example.mumble.services

import androidx.lifecycle.LifecycleService
import com.example.mumble.services.manager.UserAnnouncementManager
import com.example.mumble.services.manager.UserDiscoveryManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * TODO: See how and when to unregister current user
 *  tip: can not unregister it on service destroy, since
 *  that can happen during recomposition (phone rotation)
 *
 */
@AndroidEntryPoint
class ChatService : LifecycleService() {

    @Inject
    lateinit var userDiscoveryManager: UserDiscoveryManager

    @Inject
    lateinit var userAnnouncementManager: UserAnnouncementManager

    override fun onCreate() {
        super.onCreate()
        userDiscoveryManager = userDiscoveryManager.apply {
            discoverChats(this@ChatService)
        }
        userAnnouncementManager = userAnnouncementManager.apply {
            observeChatAvailableAnnouncement(this@ChatService)
        }
    }

    companion object {
        const val SERVICE_TYPE = "_mumble._tcp"
    }
}
