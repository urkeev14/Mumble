package com.example.mumble.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mumble.domain.repository.source.preferences.IUserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userPreferences: IUserPreferences
) : ViewModel() {

    fun setUserIsOnboarded() = viewModelScope.launch {
        userPreferences.setIsOnboarded(true)
    }
}
