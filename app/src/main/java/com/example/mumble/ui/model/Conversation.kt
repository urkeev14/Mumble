package com.example.mumble.ui.model

import java.util.UUID

data class Conversation(
    val id: UUID,
    val messages: List<Message>
) {
    fun getParticipants() = messages.map { it.creator }.distinct()
}
