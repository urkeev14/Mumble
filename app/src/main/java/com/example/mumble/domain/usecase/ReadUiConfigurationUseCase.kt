package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.domain.repository.IUiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadUiConfigurationUseCase @Inject constructor(
    private val repository: IUiRepository
) {

    operator fun invoke(): Flow<UiConfiguration> {
        return repository.getUiConfig()
    }
}
