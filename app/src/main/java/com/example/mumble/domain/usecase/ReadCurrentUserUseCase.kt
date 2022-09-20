package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCurrentUserUseCase @Inject constructor(
    private val repository: IChatRepository,
) {
    operator fun invoke(): Flow<UserEntity> {
        return repository.getCurrentUser()
    }
}
