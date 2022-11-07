package com.example.mumble.data.repository.impl

import com.example.mumble.domain.model.ToolbarConfiguration
import com.example.mumble.domain.model.UiConfiguration
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

    private val uiConfiguration: MutableStateFlow<UiConfiguration> = MutableStateFlow(
        UiConfiguration(ToolbarConfiguration(isVisible = false, screen = Screen.Splash))
    )
    private val error: MutableSharedFlow<UiMessage> = MutableSharedFlow()

    override fun updateUiConfiguration(value: UiConfiguration) {
        uiConfiguration.update { value }
    }

    override fun getUiConfig(): Flow<UiConfiguration> {
        return uiConfiguration.asStateFlow()
    }

    override suspend fun setMessage(value: UiMessage) {
        error.emit(value)
    }

    override fun getMessage(): Flow<UiMessage> {
        return error.asSharedFlow()
    }
}
