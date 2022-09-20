package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.User
import com.example.mumble.domain.repository.IChatRepository
import javax.inject.Inject

class UpdateCurrentUserUseCase @Inject constructor(
    private val repository: IChatRepository
) {

    suspend operator fun invoke(user: User) {
        repository.updateCurrentUser(user)
    }
}
