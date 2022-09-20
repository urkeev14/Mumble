package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IChatRepository
import java.util.UUID
import javax.inject.Inject

class CreateConversationUseCase @Inject constructor(
    private val repository: IChatRepository
) {

    operator fun invoke(participants: List<UUID>): UUID {
        return repository.createConversation(participants)
    }
}
