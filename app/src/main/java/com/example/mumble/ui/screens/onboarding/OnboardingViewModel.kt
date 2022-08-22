package com.example.mumble.ui.screens.onboarding

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {

    val isLogoVisible = mutableStateOf(false)

    init {
        startLogoAnimation()
    }

    private fun startLogoAnimation() {
        viewModelScope.launch {
            delay(SHORT_DELAY)
            isLogoVisible.value = true
            delay(LONG_DELAY)
            isLogoVisible.value = false
        }
    }

    companion object {
        const val ANIMATION_TIME = 2000
        const val SHORT_DELAY = 500L
        const val LONG_DELAY = 3000L
    }
}
