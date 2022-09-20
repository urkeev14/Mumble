package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IUiRepository
import com.example.mumble.utils.UiMessage
import javax.inject.Inject

class SetUiMessageUseCase @Inject constructor(
    private val repository: IUiRepository
) {
    suspend operator fun invoke(message: UiMessage) {
        repository.setMessage(message)
    }
}
