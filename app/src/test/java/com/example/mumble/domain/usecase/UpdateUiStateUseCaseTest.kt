package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UiState
import com.example.mumble.domain.repository.IUiRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateUiStateUseCaseTest {

    private lateinit var repository: IUiRepository
    private lateinit var sut: UpdateUiStateUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = UpdateUiStateUseCase(repository)
    }

    @Test
    fun `invoke sets connectivity state`() = runBlocking {
        val uiState = mockk<UiState>()
        // Given that repository will update [UiConfiguration] successfully
        coEvery { repository.updateUiConfiguration(any()) } returns Unit

        // When use case is invoked
        sut(uiState)

        // Then repository sets new [UiConfiguration] value
        coVerify(exactly = 1) { repository.updateUiConfiguration(uiState) }
    }
}
