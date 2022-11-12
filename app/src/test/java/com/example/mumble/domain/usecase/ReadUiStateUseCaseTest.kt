package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UiState
import com.example.mumble.domain.repository.IUiRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class ReadUiStateUseCaseTest {

    private lateinit var repository: IUiRepository
    private lateinit var sut: ReadUiConfigurationUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = ReadUiConfigurationUseCase(repository)
    }

    @Test
    fun `invoke returns UiConfiguration when repository returns UiConfiguration`() = runBlocking {
        val expected = UiState()

        every { repository.getUiConfig() } returns flowOf(expected)

        val actual = sut().first()

        assertEquals(expected, actual)
    }
}
