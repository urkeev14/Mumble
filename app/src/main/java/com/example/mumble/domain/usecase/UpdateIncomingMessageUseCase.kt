package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.repository.IChatRepository
import javax.inject.Inject

class UpdateIncomingMessageUseCase @Inject constructor(
    private val repository: IChatRepository
) {

    suspend operator fun invoke(message: MessageEntity) {
        repository.updateIncomingMessage(message)
    }
}
