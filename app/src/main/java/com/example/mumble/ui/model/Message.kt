package com.example.mumble.ui.model

import java.util.UUID

data class Message(
    val id: UUID,
    val content: String,
    val time: String,
    val creator: User,
)
