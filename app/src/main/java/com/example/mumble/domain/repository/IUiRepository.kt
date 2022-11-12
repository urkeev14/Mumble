package com.example.mumble.domain.repository

import com.example.mumble.domain.model.UiState
import com.example.mumble.utils.UiMessage
import kotlinx.coroutines.flow.Flow

interface IUiRepository {
    /**
     * Updates UI configuration (things like app toolbar title, colors etc.)
     *
     * @param value value of a new configuration
     */
    fun updateUiConfiguration(value: UiState)

    /**
     * Returns UI configuration
     */
    fun getUiConfig(): Flow<UiState>

    /**
     * Sets one-time message used to be shown to user through dialog, snackbar etc.
     *
     * @param value new message to be shown
     */
    suspend fun setMessage(value: UiMessage)

    /**
     * Returns one-time message
     */
    fun getMessage(): Flow<UiMessage>
}
