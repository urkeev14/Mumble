package com.example.mumble.ui.navigation

import com.example.mumble.ui.screens.chats.ChatsTabs

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Introduction : Screen("introduction")

    open class Chats(route: String) : Screen(route) {
        object Conversations : Chats("conversations")
        object AvailableUsers : Chats("availableUsers")

        companion object {
            fun getFromTab(tab: ChatsTabs): Screen {
                return when (tab) {
                    ChatsTabs.AvailableUsers -> AvailableUsers
                    ChatsTabs.Conversations -> Conversations
                }
            }
        }
    }

    class Chat(uuid: String? = "{userId}") : Screen("chat/$uuid")
}
