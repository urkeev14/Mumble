package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.AvatarEntity
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.ui.theme.RandomColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: IChatRepository,
    private val readAllUsersOnlineUseCase: ReadAllUsersOnlineUseCase
) {
    suspend operator fun invoke(userEntity: UserEntity) = withContext(Dispatchers.Default) {
        val usersCount = readAllUsersOnlineUseCase().first().count()
        val newUserColor = RandomColors[RandomColors.lastIndex - (usersCount % RandomColors.lastIndex)]
        val userWithAssignedColor = userEntity.copy(
            avatar = AvatarEntity(
                monogram = userEntity.username.firstOrNull().toString(),
                color = newUserColor.value
            )
        )
        repository.createUser(userWithAssignedColor)
    }
}
