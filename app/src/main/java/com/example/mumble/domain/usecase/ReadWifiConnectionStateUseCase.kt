package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IConnectivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Gets wifi connection state from [IConnectivityRepository]
 * This use case is supposed to provide flow to connectivity state
 * so that a proper message is shown when user is disconnected from WiFi.
 *
 * @property repository repository to fetch connection state from
 */
class ReadWifiConnectionStateUseCase @Inject constructor(
    private val repository: IConnectivityRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return repository.getConnectedToWifi()
    }
}
