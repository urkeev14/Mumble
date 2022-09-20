package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IChatRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class DeleteUserUseCaseTest {
    private lateinit var repository: IChatRepository
    private lateinit var sut: DeleteUserUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = DeleteUserUseCase(repository)
    }

    @Test
    fun `invoke returns nickname flow`() = runBlocking {
        val nickname = "some_nickname"
        // Given that repository will successfully delete user
        every { repository.deleteUser(nickname) } returns Unit

        // When use case is invoked
        sut(nickname)

        // Then repository method for deleting user is called once
        verify(exactly = 1) { repository.deleteUser(nickname) }
    }
}
