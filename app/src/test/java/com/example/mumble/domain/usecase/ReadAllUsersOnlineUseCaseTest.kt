package com.example.mumble.domain.usecase

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.ui.model.User
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
    private lateinit var mapper: Mapper<UserEntity, User>
    private lateinit var sut: ReadAllUsersOnlineUseCase

    @Before
    fun setUp() {
        repository = mockk()
        mapper = mockk()
        sut = ReadAllUsersOnlineUseCase(repository, mapper)
    }

    @Test
    fun `invoke return all online users when repository returns all online users`() = runBlocking {
        val expected = listOf<UserEntity>()

        every { repository.getAllUsers() } returns flowOf(expected)
        every { mapper.map(any<List<UserEntity>>()) } returns emptyList()

        val actual = sut().first()

        assertEquals(expected, actual)
    }
}
