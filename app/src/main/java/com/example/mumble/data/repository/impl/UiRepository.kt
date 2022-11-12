package com.example.mumble.data.repository.impl

import com.example.mumble.domain.model.ToolbarState
import com.example.mumble.domain.model.UiState
import com.example.mumble.domain.repository.IUiRepository
import com.example.mumble.ui.navigation.Screen
import com.example.mumble.utils.UiMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UiRepository : IUiRepository {

    private val uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(ToolbarState(isVisible = false, screen = Screen.Splash))
    )
    private val error: MutableSharedFlow<UiMessage> = MutableSharedFlow()

    override fun updateUiConfiguration(value: UiState) {
        uiState.update { value }
    }

    override fun getUiConfig(): Flow<UiState> {
        return uiState.asStateFlow()
    }

    override suspend fun setMessage(value: UiMessage) {
        error.emit(value)
    }

    override fun getMessage(): Flow<UiMessage> {
        return error.asSharedFlow()
    }
}
