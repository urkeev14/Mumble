package com.example.mumble.domain.usecase

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.dto.MessageDto
import com.example.mumble.domain.dto.OutgoingMessageDto
import com.example.mumble.domain.dto.UserDto
import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadOutgoingMessageUseCase @Inject constructor(
    private val repository: IChatRepository,
    private val messageMapper: Mapper<MessageEntity, MessageDto>,
    private val userMapper: Mapper<UserEntity, UserDto>
) {

    suspend operator fun invoke(): Flow<OutgoingMessageDto> {
        return repository.getOutgoingMessage().map { input ->
            val users = repository.getUsers(input.recipients).first().map { userMapper.map(it) }
            val message = messageMapper.map(input.messageEntity).copy(
                recipientsIds = users.map { it.id.toString() }
            )
            OutgoingMessageDto(message, users)
        }
    }
}
