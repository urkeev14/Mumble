package com.example.mumble.domain.usecase

import com.example.mumble.data.repository.IChatRepository
import com.example.mumble.domain.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class CreateUserUseCaseTest {
    private lateinit var repository: IChatRepository
    private lateinit var sut: CreateUserUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = CreateUserUseCase(repository)
    }

    @Test
    fun `invoke returns nickname flow`() = runBlocking {
        val user = mockk<User>()
        // Given that repository will successfully creates user
        coEvery { repository.createUser(user) } returns Unit

        // When use case is invoked
        sut(user)

        // Then repository method for creating user is called once
        coVerify(exactly = 1) { repository.createUser(user) }
    }
}
