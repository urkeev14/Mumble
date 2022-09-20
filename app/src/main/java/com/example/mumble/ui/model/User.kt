package com.example.mumble.ui.model

import java.util.UUID

data class User(
    val id: UUID,
    val username: String,
    val isCurrentUser: Boolean,
    val isOnline: Boolean,
    val avatar: Avatar
)
