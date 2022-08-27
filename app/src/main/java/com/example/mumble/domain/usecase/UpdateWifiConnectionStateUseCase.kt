package com.example.mumble.domain.usecase

import com.example.mumble.data.repository.IConnectivityRepository
import javax.inject.Inject

/**
 * Passes wifi connection state to [IConnectivityRepository]
 * This use case is supposed to be triggered on connectivity state change
 * so that a proper message is shown when user is disconnected from WiFi.
 *
 * @property repository repository to store connection to
 */
class UpdateWifiConnectionStateUseCase @Inject constructor(
    private val repository: IConnectivityRepository
) {

    suspend operator fun invoke(isConnected: Boolean) {
        repository.setConnectedToWifi(isConnected)
    }
}
