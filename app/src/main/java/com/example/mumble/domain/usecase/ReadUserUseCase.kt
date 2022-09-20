package com.example.mumble.domain.usecase

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.ui.model.User
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class ReadUserUseCase @Inject constructor(
    private val repository: IChatRepository,
    private val mapper: Mapper<UserEntity, User>
) {
    suspend operator fun invoke(id: UUID): User {
        return mapper.map(repository.getUser(id).first())
    }
}
