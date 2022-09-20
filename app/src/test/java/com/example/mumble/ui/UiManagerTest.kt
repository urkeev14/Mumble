package com.example.mumble.ui

import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.domain.usecase.SetUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateUiConfigurationUseCase
import com.example.mumble.utils.UiMessage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class UiManagerTest {

    private lateinit var updateUiConfiguration: UpdateUiConfigurationUseCase
    private lateinit var setUiMessageUseCase: SetUiMessageUseCase
    private lateinit var sut: IUiManager

    @Before
    fun setUp() {
        updateUiConfiguration = mockk()
        setUiMessageUseCase = mockk()
        sut = UiManager(updateUiConfiguration, setUiMessageUseCase)
    }

    @Test
    fun `updateUiConfiguration calls appropriate use case`() = runBlocking {
        val uiConfiguration = UiConfiguration()
        coEvery { updateUiConfiguration(any()) } returns Unit

        sut.updateUiConfiguration(uiConfiguration)

        coVerify(exactly = 1) { updateUiConfiguration(uiConfiguration) }
    }

    @Test
    fun `setUiMessage calls appropriate use case`() = runBlocking {
        val message = UiMessage.SimpleString("some message")
        coEvery { setUiMessageUseCase(any()) } returns Unit

        sut.setUiMessage(message)

        coVerify(exactly = 1) { setUiMessageUseCase(message) }
    }
}
