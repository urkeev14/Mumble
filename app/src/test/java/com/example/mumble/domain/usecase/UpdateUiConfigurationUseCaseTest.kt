package com.example.mumble.domain.usecase

import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.domain.repository.IUiRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateUiConfigurationUseCaseTest {

    private lateinit var repository: IUiRepository
    private lateinit var sut: UpdateUiConfigurationUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = UpdateUiConfigurationUseCase(repository)
    }

    @Test
    fun `invoke sets connectivity state`() = runBlocking {
        val uiConfiguration = mockk<UiConfiguration>()
        // Given that repository will update [UiConfiguration] successfully
        coEvery { repository.updateUiConfiguration(any()) } returns Unit

        // When use case is invoked
        sut(uiConfiguration)

        // Then repository sets new [UiConfiguration] value
        coVerify(exactly = 1) { repository.updateUiConfiguration(uiConfiguration) }
    }
}
