package com.example.mumble.domain.repository

import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.utils.UiMessage
import kotlinx.coroutines.flow.Flow

interface IUiRepository {
    /**
     * Updates UI configuration (things like app toolbar title, colors etc.)
     *
     * @param value value of a new configuration
     */
    suspend fun updateUiConfiguration(value: UiConfiguration)

    /**
     * Returns UI configuration
     */
    fun getUiConfig(): Flow<UiConfiguration>

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
