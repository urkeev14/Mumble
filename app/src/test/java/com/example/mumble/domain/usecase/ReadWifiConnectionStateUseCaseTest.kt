package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IConnectivityRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class ReadWifiConnectionStateUseCaseTest {

    private lateinit var repository: IConnectivityRepository
    private lateinit var sut: ReadWifiConnectionStateUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sut = ReadWifiConnectionStateUseCase(repository)
    }

    @Test
    fun `invoke returns connectivity state flow`() = runBlocking {
        val expected = true
        // Given that repository will successfully return connectivity state
        every { repository.getConnectedToWifi() } returns flowOf(expected)

        // When use case is invoked
        val actual = sut().first()

        // Then repository getter method will be called once
        verify(exactly = 1) { repository.getConnectedToWifi() }
        // and username will be retrieved
        assertEquals(expected, actual)
    }
}
