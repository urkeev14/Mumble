package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IUiRepository
import com.example.mumble.utils.UiMessage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class SetUiMessageUseCaseTestEntity {
    private lateinit var repository: IUiRepository
    private lateinit var sut: SetUiMessageUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = SetUiMessageUseCase(repository)
    }

    @Test
    fun `invoke returns nickname flow`() = runBlocking {
        val message = UiMessage.SimpleString("message")
        // Given that repository will successfully set UI message
        coEvery { repository.setMessage(message) } returns Unit

        // When use case is invoked
        sut(message)

        // Then repository method for setting UI message is called once
        coVerify(exactly = 1) { repository.setMessage(message) }
    }
}
