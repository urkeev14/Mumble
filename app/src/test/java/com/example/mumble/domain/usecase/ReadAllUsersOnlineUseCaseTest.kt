package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.utils.OtherUsersOnline
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class ReadAllUsersOnlineUseCaseTest {

    private lateinit var repository: IChatRepository
    private lateinit var sut: ReadAllUsersOnlineUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = ReadAllUsersOnlineUseCase(repository)
    }

    @Test
    fun `invoke return all online users when repository returns all online users`() = runBlocking {
        val expected = mockk<OtherUsersOnline>()

        every { repository.getAllUsers() } returns flowOf(expected)

        val actual = sut().first()

        assertEquals(expected, actual)
    }
}
