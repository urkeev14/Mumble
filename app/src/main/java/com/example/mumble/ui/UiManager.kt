package com.example.mumble.ui

import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.domain.usecase.SetUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateUiConfigurationUseCase
import com.example.mumble.utils.UiMessage
import javax.inject.Inject

interface IUiManager {
    suspend fun updateUiConfiguration(uiConfiguration: UiConfiguration)
    suspend fun setUiMessage(uiMessage: UiMessage)
}

class UiManager @Inject constructor(
    private val updateUiConfigurationUseCase: UpdateUiConfigurationUseCase,
    private val setUiMessageUseCase: SetUiMessageUseCase
) : IUiManager {

    override suspend fun updateUiConfiguration(uiConfiguration: UiConfiguration) {
        updateUiConfigurationUseCase(uiConfiguration)
    }

    override suspend fun setUiMessage(uiMessage: UiMessage) {
        setUiMessageUseCase(uiMessage)
    }
}
