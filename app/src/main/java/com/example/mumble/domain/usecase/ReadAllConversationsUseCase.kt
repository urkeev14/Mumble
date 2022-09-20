package com.example.mumble.domain.usecase

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.ui.model.Conversation
import com.example.mumble.ui.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadAllConversationsUseCase @Inject constructor(
    private val repository: IChatRepository,
    private val messageMapper: Mapper<MessageWithUser, Message>
) {

    operator fun invoke(): Flow<List<Conversation>> {
        return repository.getConversations().map {
            it.map { conversation ->
                val users = repository.getUsers(conversation.participants).first()
                val messages = conversation.messages.map { message ->
                    MessageWithUser(
                        users.first { user -> user.id == message.conversationId },
                        message
                    )
                }
                Conversation(id = conversation.id, messageMapper.map(messages))
            }
        }
    }
}
