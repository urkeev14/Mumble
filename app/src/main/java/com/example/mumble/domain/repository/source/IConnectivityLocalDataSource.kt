package com.example.mumble.domain.repository.source

import kotlinx.coroutines.flow.Flow

interface IConnectivityLocalDataSource {
    /**
     * Sets WiFi connection state
     *
     * @param isConnected indicator if a users phone is connected to WiFi
     */
    suspend fun setConnectedToWifi(isConnected: Boolean)

    /**
     * Gets Wifi connection state
     *
     * @return indicator if a users phone is connected to WiFi
     */
    fun getConnectedToWifi(): Flow<Boolean>
}
