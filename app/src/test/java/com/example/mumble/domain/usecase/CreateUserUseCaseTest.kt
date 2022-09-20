package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.Avatar
import com.example.mumble.domain.model.User
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.ui.theme.RandomColors
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class CreateUserUseCaseTest {
    private lateinit var repository: IChatRepository
    private lateinit var readAllUsersOnlineUseCase: ReadAllUsersOnlineUseCase
    private lateinit var sut: CreateUserUseCase

    @Before
    fun setUp() {
        repository = mockk()
        readAllUsersOnlineUseCase = mockk()
        sut = CreateUserUseCase(repository, readAllUsersOnlineUseCase)
    }

    @Test
    fun `invoke returns nickname flow`() = runBlocking {
        val user = User()
        val emptyList = emptyList<User>()

        coEvery { repository.createUser(any()) } returns Unit

        every { readAllUsersOnlineUseCase() } returns flowOf(emptyList)
        val userWithAttachedColor = user.copy(
            avatar = Avatar(
                color = RandomColors[RandomColors.lastIndex - (emptyList.size % RandomColors.lastIndex)],
                monogram = user.username.firstOrNull().toString()
            )
        )

        // When use case is invoked
        sut(userWithAttachedColor)

        // Then repository method for creating user is called once
        coVerify(exactly = 1) { repository.createUser(userWithAttachedColor) }
    }
}
