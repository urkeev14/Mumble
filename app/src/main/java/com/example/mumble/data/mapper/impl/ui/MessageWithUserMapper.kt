package com.example.mumble.data.mapper.impl.ui

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.usecase.MessageWithUser
import com.example.mumble.ui.model.Message
import com.example.mumble.ui.model.User
import javax.inject.Inject

class MessageWithUserMapper @Inject constructor(
    private val userMapper: Mapper<UserEntity, User>,
    private val dateMapper: Mapper<Long, String>
) : Mapper<MessageWithUser, Message> {
    override fun map(input: MessageWithUser): Message {
        return Message(
            id = input.message.id,
            content = input.message.content,
            time = dateMapper.map(input.message.time),
            creator = userMapper.map(input.user)
        )
    }
}
