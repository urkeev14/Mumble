package com.example.mumble.data.repository.source.local.impl

import com.example.mumble.data.repository.source.local.IConnectivityLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ConnectivityLocalDataSource : IConnectivityLocalDataSource {

    private val _isConnected: MutableStateFlow<Boolean> = MutableStateFlow(true)

    override suspend fun setConnectedToWifi(isConnected: Boolean) {
        _isConnected.emit(isConnected)
    }

    override fun getConnectedToWifi(): Flow<Boolean> {
        return _isConnected
    }
}
