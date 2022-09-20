package com.example.mumble.app

import android.app.Application
import android.content.Intent
import com.example.mumble.services.ChatService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MumbleApplication : Application() {
    override fun onTerminate() {
        stopChatAnnouncementService()
        super.onTerminate()
    }

    private fun stopChatAnnouncementService() {
        val intent = Intent(this, ChatService::class.java)
        stopService(intent)
    }
}
