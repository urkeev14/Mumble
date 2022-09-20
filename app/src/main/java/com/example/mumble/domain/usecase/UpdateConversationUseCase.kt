package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.repository.IChatRepository
import java.util.UUID
import javax.inject.Inject

class UpdateConversationUseCase @Inject constructor(
    private val repository: IChatRepository,
) {

    operator fun invoke(
        conversationId: UUID,
        usersInConversation: List<UUID>,
        message: MessageEntity
    ) {
        repository.updateConversation(conversationId, usersInConversation, message)
    }
}
