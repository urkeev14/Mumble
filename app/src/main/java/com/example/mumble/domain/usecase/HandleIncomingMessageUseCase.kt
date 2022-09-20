package com.example.mumble.domain.usecase

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.dto.MessageDto
import com.example.mumble.domain.model.MessageEntity
import java.util.UUID
import javax.inject.Inject

/**
 * When message is received, we have to check if there is existing
 * conversation with [message.conversationId].
 * Yes:
 *      We update conversation with new message
 * No:
 *      We create conversation with new messgae
 * Finally, we update incoming message shared flow to receive eventual notification
 *
 * @property repository
 * @property mapper
 */
class HandleIncomingMessageUseCase @Inject constructor(
    private val updateIncomingMessageUseCase: UpdateIncomingMessageUseCase,
    private val updateConversationUseCase: UpdateConversationUseCase,
    private val mapper: Mapper<MessageDto, MessageEntity>
) {

    suspend operator fun invoke(message: MessageDto) {
        val entity = mapper.map(message)
        val conversationId = entity.conversationId
        val users = message.recipientsIds.plus(message.creatorId).map { UUID.fromString(it) }

        updateIncomingMessageUseCase(entity)
        updateConversationUseCase(conversationId, users, entity)
    }
}
