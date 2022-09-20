package com.example.mumble.data.mapper.impl.dto

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.dto.MessageDto
import com.example.mumble.domain.model.MessageEntity
import java.util.UUID

class MessageDtoMapper : Mapper<MessageDto, MessageEntity> {
    override fun map(input: MessageDto): MessageEntity {
        return MessageEntity(
            id = UUID.fromString(input.id),
            conversationId = UUID.fromString(input.id),
            content = input.content,
            creatorId = UUID.fromString(input.creatorId),
            time = input.time
        )
    }
}
