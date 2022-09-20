package com.example.mumble.ui.screens.chats

import androidx.annotation.StringRes
import com.example.mumble.R

sealed class ChatsTabs(var index: Int, @StringRes val tabNameStringRes: Int) {
    object Conversations : ChatsTabs(0, R.string.conversations)
    object AvailableUsers : ChatsTabs(1, R.string.available_users)

    companion object {
        fun convert(value: Int): ChatsTabs {
            return when (value) {
                0 -> Conversations
                else -> AvailableUsers
            }
        }

        fun children() = listOf(Conversations, AvailableUsers)
    }
}
