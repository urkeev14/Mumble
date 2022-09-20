package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class CreateUserUseCaseTestEntity {
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
    fun `invoke creates new user`() = runBlocking {
        val userEntity = UserEntity()

        coEvery { repository.createUser(any()) } returns Unit
        every { readAllUsersOnlineUseCase() } returns flowOf(emptyList())

        // When use case is invoked
        sut(userEntity)

        // Then repository method for creating user is called once
        coVerify(exactly = 1) { repository.createUser(any()) }
    }
}
