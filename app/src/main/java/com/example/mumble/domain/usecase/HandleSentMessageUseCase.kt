package com.example.mumble.domain.usecase

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.dto.MessageDto
import com.example.mumble.domain.model.MessageEntity
import java.util.UUID
import javax.inject.Inject

/**
 * This use case is triggered upon sending a message through socket
 * in [MessageManager] when outgoing message shared flow is triggered.
 * Therefor we know that this field is already updated.
 * Two things need to happen here:
 *  1. We need to update list of all the messages
 *  2. We need to update conversation with a new message
 *
 *  TODO: Thing about how to remove [MessageEntity.recipientsIds] and move them to
 *  [Conversation.users]
 *
 * @property updateConversationUseCase
 * @property updateMessagesUseCase
 * @property mapper
 */
class HandleSentMessageUseCase @Inject constructor(
    private val updateConversationUseCase: UpdateConversationUseCase,
    private val mapper: Mapper<MessageDto, MessageEntity>
) {

    operator fun invoke(message: MessageDto) {
        val entity = mapper.map(message)
        val conversationId = UUID.fromString(message.conversationId)
        val users = message.recipientsIds.plus(message.creatorId).map { UUID.fromString(it) }

        updateConversationUseCase(conversationId, users, entity)
    }
}
