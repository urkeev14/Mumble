package com.example.mumble.domain.usecase

import com.example.mumble.data.repository.IConnectivityRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateWifiConnectionStateUseCaseTest {

    private lateinit var repository: IConnectivityRepository
    private lateinit var sut: UpdateWifiConnectionStateUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = UpdateWifiConnectionStateUseCase(repository)
    }

    @Test
    fun `invoke sets connectivity state`() = runBlocking {
        val expected = true
        // Given that repository will set connectivity successfully
        coEvery { repository.setConnectedToWifi(expected) } returns Unit

        // When use case is invoked
        sut(expected)

        // Then repository sets new connectivity state
        coVerify(exactly = 1) { repository.setConnectedToWifi(expected) }
    }
}
