package com.example.mumble.data.repository.impl

import com.example.mumble.data.repository.IUiRepository
import com.example.mumble.utils.UiMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class UiRepository : IUiRepository {

    private val error: MutableSharedFlow<UiMessage> = MutableSharedFlow()

    override suspend fun setMessage(message: UiMessage) {
        error.emit(message)
    }

    override fun getMessage(): Flow<UiMessage> {
        return error.asSharedFlow()
    }
}
