package com.example.mumble.data.repository.impl

import com.example.mumble.domain.repository.IConnectivityRepository
import com.example.mumble.domain.repository.source.IConnectivityLocalDataSource
import kotlinx.coroutines.flow.Flow

class ConnectivityRepository(
    private val localDataSource: IConnectivityLocalDataSource
) : IConnectivityRepository {
    override suspend fun setConnectedToWifi(isConnected: Boolean) {
        localDataSource.setConnectedToWifi(isConnected)
    }

    override fun getConnectedToWifi(): Flow<Boolean> {
        return localDataSource.getConnectedToWifi()
    }
}
