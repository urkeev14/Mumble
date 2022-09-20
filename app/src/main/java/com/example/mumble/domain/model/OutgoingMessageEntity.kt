package com.example.mumble.domain.model

import java.util.UUID

data class OutgoingMessageEntity(
    val recipients: List<UUID>,
    val messageEntity: MessageEntity,
) {
    val content = messageEntity.content
    val conversationId = messageEntity.conversationId
}
