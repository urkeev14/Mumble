package com.example.mumble.domain.model

import java.util.UUID

/**
 * Conversation between multiple users
 *
 * @property id uniquely identifies conversation
 * @property participants users involved in conversation
 * @property messages messages exchanged between [participants]
 */
data class ConversationEntity(
    val id: UUID = UUID.randomUUID(),
    val participants: List<UUID>,
    val messages: List<MessageEntity> = listOf()
) {

    /**
     * Compares conversation by its' users
     *
     * @param users users having a conversation
     * @return returns true if and only if all users are part of this conversation (and nobody else)
     */
    fun isBetween(users: List<UUID>): Boolean {
        return this.participants == users
    }

    fun add(message: MessageEntity): ConversationEntity {
        return copy(messages = messages.plus(message))
    }

    override fun equals(other: Any?): Boolean {
        return other is ConversationEntity && other.id == id
    }

    override fun hashCode(): Int {
        var result = participants.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
