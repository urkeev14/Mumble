package com.example.mumble.ui.screens.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mumble.domain.repository.source.preferences.IUserPreferences
import com.example.mumble.ui.IUiManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val uiManager: IUiManager,
    private val userPreferences: IUserPreferences
) : ViewModel(), IUiManager by uiManager {

    init {
        startLogoAnimation()
    }

    val isLogoVisible = mutableStateOf(false)

    private val _isSplashAnimationOver = MutableSharedFlow<Boolean>()
    val isSplashAnimationOver = _isSplashAnimationOver.asSharedFlow()

    private fun startLogoAnimation() {
        viewModelScope.launch {
            delay(SHORT_DELAY)
            isLogoVisible.value = true
            delay(SHORT_DELAY * 4)
            _isSplashAnimationOver.emit(true)
        }
    }

    suspend fun getIsUserOnboarded(): Boolean {
        return userPreferences.getIsOnboarded().first()
    }

    companion object {
        const val ANIMATION_TIME = 2000
        const val SHORT_DELAY = 500L
        const val LONG_DELAY = 3000L
    }
}
