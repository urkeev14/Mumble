package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UiState
import com.example.mumble.domain.repository.IUiRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateUiStateUseCase @Inject constructor(
    private val repository: IUiRepository
) {

    suspend operator fun invoke(uiState: UiState) {
        val previousConfiguration = repository.getUiConfig().first()
        if (previousConfiguration == uiState) return

        repository.updateUiConfiguration(uiState)
    }
}
