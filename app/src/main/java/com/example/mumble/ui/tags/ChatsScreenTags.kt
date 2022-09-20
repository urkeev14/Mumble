package com.example.mumble.ui.tags

enum class ChatsScreenTags {
    ChatUsersWithSearch {
        override fun toString() = "chatsScreenWithSearch"
    },
    NobodyOnlineScreen {
        override fun toString() = "nobodyOnlineScreen"
    },
    Search {
        override fun toString() = "search"
    },
    Users {
        override fun toString() = "users"
    }
}
