package com.example.mumble.domain.usecase

import com.example.mumble.data.repository.IUiRepository
import com.example.mumble.utils.UiMessage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class ReadUiMessageUseCaseTest {
    private lateinit var repository: IUiRepository
    private lateinit var sut: ReadUiMessageUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = ReadUiMessageUseCase(repository)
    }

    @Test
    fun `invoke returns nickname flow`() = runBlocking {

        // Given that repository will successfully read UI message
        every { repository.getMessage() } returns flowOf(UiMessage.SimpleString("message"))

        // When use case is invoked
        sut()

        // Then repository method for reading UI message is called once
        verify(exactly = 1) { repository.getMessage() }
    }
}
