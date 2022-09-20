package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IChatRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class UpdateCurrentUsersNicknameUseCaseTest {

    private lateinit var repository: IChatRepository
    private lateinit var sut: UpdateCurrentUsersNicknameUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = UpdateCurrentUsersNicknameUseCase(repository)
    }

    @Test
    fun `invoke sets new nickname`() = runBlocking {
        val expected = "nickname"
        // Given that repository will successfully set current users nickname
        coEvery { repository.setCurrentUsersNickname(expected) } returns Unit

        // When use case is invoked
        sut(expected)

        // Then repository method for settings current users nickname will be invoked once
        coVerify(exactly = 1) { repository.setCurrentUsersNickname(expected) }
    }
}
