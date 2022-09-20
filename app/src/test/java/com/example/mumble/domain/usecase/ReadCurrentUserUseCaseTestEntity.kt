package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class ReadCurrentUserUseCaseTestEntity {

    private lateinit var repository: IChatRepository
    private lateinit var sut: ReadCurrentUserUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = ReadCurrentUserUseCase(repository)
    }

    @Test
    fun `invoke returns nickname flow`() = runBlocking {
        val userEntity = mockk<UserEntity>()
        // Given that repository will successfully return currentUser
        coEvery { repository.getCurrentUser() } returns flowOf(userEntity)

        // When use case is invoked
        sut()

        // Then repository method for getting a user is called once
        coVerify(exactly = 1) { repository.getCurrentUser() }
    }
}
