package com.example.mumble.data.repository.source.local.impl

import com.example.mumble.data.repository.source.local.ConnectivityLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ConnectivityLocalDataSourceTest {

    private val sut: ConnectivityLocalDataSource = ConnectivityLocalDataSource()

    @Test
    fun `setConnectedToWifi successfully sets connectivity status to true`() = runBlocking {
        sut.setConnectedToWifi(CONNECTED)
        val expected = CONNECTED
        val actual = sut.getConnectedToWifi().first()
        assertEquals(expected, actual)
    }

    @Test
    fun `setConnectedToWifi successfully sets connectivity status to false`() = runBlocking {
        sut.setConnectedToWifi(DISCONNECTED)
        val expected = DISCONNECTED
        val actual = sut.getConnectedToWifi().first()
        assertEquals(expected, actual)
    }

    companion object {
        const val CONNECTED = true
        const val DISCONNECTED = false
    }
}
