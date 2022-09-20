package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadUserByIpAddressUseCase @Inject constructor(
    private val repository: IChatRepository
) {
    suspend operator fun invoke(host: String): UserEntity? {
        return repository.getAllUsers().map { users ->
            users.firstOrNull {
                it.host == host
            }
        }.first()
    }
}
