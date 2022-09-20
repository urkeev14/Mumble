package com.example.mumble.ui.screens.introduction

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mumble.domain.usecase.UpdateCurrentUsersNicknameUseCase
import com.example.mumble.ui.IUiManager
import com.example.mumble.utils.State
import com.example.mumble.utils.UiMessage
import com.example.mumble.utils.validator.FieldValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val uiManager: IUiManager,
    private val fieldValidator: FieldValidator,
    private val updateCurrentUsersNicknameUseCase: UpdateCurrentUsersNicknameUseCase,
) : ViewModel(), IUiManager by uiManager {

    val nickname = mutableStateOf(NO_NICKNAME)

    val stateErrorMessage: MutableStateFlow<UiMessage?> = MutableStateFlow(null)
    val stateGoToChatsScreen: MutableSharedFlow<Boolean> = MutableSharedFlow()

    companion object {
        const val NO_NICKNAME = ""
    }

    fun validateNickname() {
        when (val state = fieldValidator.validate(nickname.value)) {
            is State.Outcome.Error -> handleInvalidNickname(state)
            is State.Outcome.Success -> handleValidNickname()
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun handleValidNickname() {
        viewModelScope.launch {
            stateErrorMessage.value = null
            updateCurrentUsersNicknameUseCase(nickname.value)
            stateGoToChatsScreen.emit(true)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun handleInvalidNickname(errorState: State.Outcome<Unit>) {
        stateErrorMessage.value = errorState.message
    }

    fun setNickname(value: String) {
        nickname.value = value
    }
}
