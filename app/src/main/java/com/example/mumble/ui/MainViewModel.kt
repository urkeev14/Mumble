package com.example.mumble.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mumble.domain.model.UiState
import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.domain.usecase.ReadUiConfigurationUseCase
import com.example.mumble.domain.usecase.ReadUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateWifiConnectionStateUseCase
import com.example.mumble.utils.UiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateWifiConnectionStateUseCase: UpdateWifiConnectionStateUseCase,
    readUiConfigurationUseCase: ReadUiConfigurationUseCase,
    readUiMessageUseCase: ReadUiMessageUseCase,
    readCurrentUserUseCase: ReadCurrentUserUseCase
) : ViewModel() {

    val currentUser = readCurrentUserUseCase().shareIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily
    )

    val uiMessage: SharedFlow<UiMessage> = readUiMessageUseCase().shareIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily
    )

    val uiState: StateFlow<UiState> = readUiConfigurationUseCase().stateIn(
        viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = UiState()
    )

    fun setWifiConnectionAvailable(isAvailable: Boolean) {
        viewModelScope.launch {
            updateWifiConnectionStateUseCase(isAvailable)
        }
    }
}
