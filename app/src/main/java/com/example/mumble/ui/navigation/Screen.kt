package com.example.mumble.ui.navigation

import com.example.mumble.ui.screens.chats.ChatsTabs

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Introduction : Screen("introduction")

    open class Chats(route: String) : Screen(route) {
        object Conversations : Chats("conversations")
        object AvailableUsers : Chats("availableUsers")

        companion object {
            fun getScreen(index: Int): Screen {
                return when (index) {
                    ChatsTabs.AvailableUsers.index -> AvailableUsers
                    ChatsTabs.Conversations.index -> Conversations
                    else -> throw Exception("Wrong tab index !")
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Screen && other.route == route
    }

    override fun hashCode(): Int {
        return route.hashCode()
    }

    class Chat(uuid: String? = "{userId}") : Screen("chat/$uuid")
}
