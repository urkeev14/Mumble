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
            delay(500)
            isLogoVisible.value = true
            delay(3000)
            isLogoVisible.value = false
        }
    }

}