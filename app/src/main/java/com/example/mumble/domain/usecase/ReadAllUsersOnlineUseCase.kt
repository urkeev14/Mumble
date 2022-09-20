package com.example.mumble.domain.usecase

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.ui.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadAllUsersOnlineUseCase @Inject constructor(
    private val repository: IChatRepository,
    private val mapper: Mapper<UserEntity, User>
) {

    operator fun invoke(): Flow<List<User>> {
        return repository.getAllUsers().map { users ->
            mapper.map(users).sortedBy { it.username }
        }
    }
}
