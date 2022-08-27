package com.example.mumble.domain.usecase

import com.example.mumble.data.repository.IChatRepository
import com.example.mumble.domain.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class UpdateCurrentUserUseCaseTest {

    private lateinit var repository: IChatRepository
    private lateinit var sut: UpdateCurrentUserUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = UpdateCurrentUserUseCase(repository)
    }

    @Test
    fun `invoke sets new nickname`() = runBlocking {
        val user = mockk<User>()
        coEvery { repository.updateCurrentUser(user) } returns Unit

        sut(user)

        coVerify(exactly = 1) { repository.updateCurrentUser(user) }
    }
}
