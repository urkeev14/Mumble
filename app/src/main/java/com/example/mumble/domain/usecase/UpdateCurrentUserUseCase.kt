package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateCurrentUserUseCase @Inject constructor(
    private val repository: IChatRepository
) {

    suspend operator fun invoke(userEntity: UserEntity) = withContext(Dispatchers.IO) {
        repository.updateCurrentUser(userEntity)
    }
}
