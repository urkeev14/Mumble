package com.example.mumble.ui.screens.chats.chat

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mumble.ui.model.Message
import com.example.mumble.ui.model.User

@Composable
fun MessageItem(modifier: Modifier = Modifier, user: User, message: Message) {
    if (user.isCurrentUser) {
        MyMessageItem(modifier, message)
    } else {
        OtherUsersMessageItem(modifier, user, message)
    }
}
