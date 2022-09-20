package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.utils.OtherUsersOnline
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAllUsersOnlineUseCase @Inject constructor(
    private val repository: IChatRepository
) {

    operator fun invoke(): Flow<OtherUsersOnline> {
        return repository.getAllUsers()
    }
}
