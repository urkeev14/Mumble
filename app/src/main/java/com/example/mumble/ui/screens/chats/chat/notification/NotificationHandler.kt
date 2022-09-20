package com.example.mumble.ui.screens.chats.chat.notification

import com.example.mumble.ui.model.Message

interface NotificationHandler {
    fun showNotification(message: Message)
}
