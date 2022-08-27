package com.example.mumble.domain.usecase

import com.example.mumble.data.repository.IChatRepository
import com.example.mumble.domain.model.User
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: IChatRepository
) {
    suspend operator fun invoke(user: User) {
        repository.createUser(user)
    }
}
