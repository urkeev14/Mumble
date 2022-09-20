package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IChatRepository
import java.util.UUID
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: IChatRepository
) {

    operator fun invoke(id: UUID) {
        repository.deleteUser(id)
    }
}
