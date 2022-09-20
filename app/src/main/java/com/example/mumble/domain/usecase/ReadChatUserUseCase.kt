package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.User
import com.example.mumble.domain.repository.IChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadChatUserUseCase @Inject constructor(
    private val repository: IChatRepository
) {
    operator fun invoke(username: String): Flow<User> {
        return repository.getAllUsers().map { users ->
            users.first {
                it.username == username
            }
        }
    }
}
