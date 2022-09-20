package com.example.mumble.data.mapper.impl.entity

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.dto.MessageDto
import com.example.mumble.domain.model.MessageEntity

class MessageEntityMapper : Mapper<MessageEntity, MessageDto> {
    override fun map(input: MessageEntity): MessageDto {
        return MessageDto(
            id = input.id.toString(),
            conversationId = input.conversationId.toString(),
            creatorId = input.creatorId.toString(),
            content = input.content,
            recipientsIds = emptyList(),
            time = input.time
        )
    }
}
