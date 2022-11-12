package com.example.mumble.ui

import com.example.mumble.domain.model.UiState
import com.example.mumble.domain.usecase.SetUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateUiStateUseCase
import com.example.mumble.utils.UiMessage
import javax.inject.Inject

interface IUiManager {
    suspend fun updateUiState(uiState: UiState)
    suspend fun setUiMessage(uiMessage: UiMessage)
}

class UiManager @Inject constructor(
    private val updateUiStateUseCase: UpdateUiStateUseCase,
    private val setUiMessageUseCase: SetUiMessageUseCase
) : IUiManager {

    override suspend fun updateUiState(uiState: UiState) {
        updateUiStateUseCase(uiState)
    }

    override suspend fun setUiMessage(uiMessage: UiMessage) {
        setUiMessageUseCase(uiMessage)
    }
}
