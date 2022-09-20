package com.example.mumble.domain.model

import java.util.Date
import java.util.UUID

// TODO: Think of how to extract [recipientsIds] to Conversation.kt
data class MessageEntity(
    val conversationId: UUID = UUID.randomUUID(),
    val id: UUID = UUID.randomUUID(),
    val content: String,
    val creatorId: UUID = UUID.randomUUID(),
    val time: Long = Date().time
) {
    var isRead: Boolean = false

    /**
     * Read a message
     */
    fun read() {
        isRead = true
    }

    override fun equals(other: Any?): Boolean {
        return other is MessageEntity &&
            other.id == id &&
            other.content == content &&
            other.creatorId == creatorId &&
            other.time == time
    }

    override fun hashCode(): Int {
        var result = content.hashCode()
        result = 31 * result + creatorId.hashCode()
        result = 31 * result + time.hashCode()
        return result
    }
}
