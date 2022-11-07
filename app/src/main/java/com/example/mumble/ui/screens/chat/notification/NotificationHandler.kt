package com.example.mumble.ui.screens.chat.notification

import com.example.mumble.ui.model.Message

interface NotificationHandler {
    fun showNotification(message: Message)
}
