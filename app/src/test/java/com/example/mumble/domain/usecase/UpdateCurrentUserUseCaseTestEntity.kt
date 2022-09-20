package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class UpdateCurrentUserUseCaseTestEntity {

    private lateinit var repository: IChatRepository
    private lateinit var sut: UpdateCurrentUserUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = UpdateCurrentUserUseCase(repository)
    }

    @Test
    fun `invoke sets new nickname`() = runBlocking {
        val userEntity = mockk<UserEntity>()
        coEvery { repository.updateCurrentUser(userEntity) } returns Unit

        sut(userEntity)

        coVerify(exactly = 1) { repository.updateCurrentUser(userEntity) }
    }
}
