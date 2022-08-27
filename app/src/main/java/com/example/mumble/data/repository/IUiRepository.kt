package com.example.mumble.data.repository

import com.example.mumble.utils.UiMessage
import kotlinx.coroutines.flow.Flow

interface IUiRepository {
    suspend fun setMessage(message: UiMessage)
    fun getMessage(): Flow<UiMessage>
}
