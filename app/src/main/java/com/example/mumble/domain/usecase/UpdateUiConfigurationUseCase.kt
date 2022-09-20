package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.domain.repository.IUiRepository
import javax.inject.Inject

class UpdateUiConfigurationUseCase @Inject constructor(
    private val repository: IUiRepository
) {

    operator fun invoke(uiConfiguration: UiConfiguration) {
        repository.updateUiConfiguration(uiConfiguration)
    }
}
