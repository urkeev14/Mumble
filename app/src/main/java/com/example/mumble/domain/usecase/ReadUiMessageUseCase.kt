package com.example.mumble.domain.usecase

import com.example.mumble.data.repository.IUiRepository
import com.example.mumble.utils.UiMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadUiMessageUseCase @Inject constructor(
    private val repository: IUiRepository
) {
    operator fun invoke(): Flow<UiMessage> {
        return repository.getMessage()
    }
}
