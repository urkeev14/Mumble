package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.model.OutgoingMessageEntity
import com.example.mumble.domain.repository.IChatRepository
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class UpdateOutgoingMessageUseCase @Inject constructor(
    private val repository: IChatRepository,
    private val readCurrentUserUseCase: ReadCurrentUserUseCase,
    private val createConversationUseCase: CreateConversationUseCase
) {

    suspend operator fun invoke(
        convId: UUID?,
        participants: List<UUID>,
        messageContent: String
    ) {
        val currentUserId = readCurrentUserUseCase().first().id
        val conversationId = convId ?: createConversationUseCase(listOf())

        val newMessage = OutgoingMessageEntity(
            messageEntity = MessageEntity(
                creatorId = currentUserId,
                conversationId = conversationId,
                content = messageContent,
            ),
            recipients = participants
        )
        repository.updateOutgoingMessage(newMessage)
    }
}
