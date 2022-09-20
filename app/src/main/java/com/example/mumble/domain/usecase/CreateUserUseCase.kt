package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.Avatar
import com.example.mumble.domain.model.User
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.ui.theme.RandomColors
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: IChatRepository,
    private val readAllUsersOnlineUseCase: ReadAllUsersOnlineUseCase
) {
    suspend operator fun invoke(user: User) {
        val usersCount = readAllUsersOnlineUseCase().first().count()
        val newUserColor = RandomColors[RandomColors.lastIndex - (usersCount % RandomColors.lastIndex)]
        val userWithAssignedColor = user.copy(
            avatar = Avatar(
                monogram = user.username.firstOrNull().toString(),
                color = newUserColor
            )
        )
        repository.createUser(userWithAssignedColor)
    }
}
