package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.domain.repository.IUiRepository

class UpdateUiConfigurationUseCase(
    private val repository: IUiRepository
) {

    suspend operator fun invoke(uiConfiguration: UiConfiguration) {
        repository.updateUiConfiguration(uiConfiguration)
    }
}
