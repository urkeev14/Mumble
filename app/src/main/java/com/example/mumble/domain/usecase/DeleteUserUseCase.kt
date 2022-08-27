package com.example.mumble.domain.usecase

import com.example.mumble.data.repository.IChatRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: IChatRepository
) {

    operator fun invoke(nickname: String) {
        repository.deleteUser(nickname)
    }
}
