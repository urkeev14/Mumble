package com.example.mumble.ui

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.mumble.services.ChatService
import com.example.mumble.ui.theme.MumbleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val hasWifi: Boolean = networkCapabilities.hasCapability(
                NetworkCapabilities.TRANSPORT_WIFI
            )
            setWifiConnectionAvailable(hasWifi)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            setWifiConnectionAvailable(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MumbleTheme {
                App()
            }
        }
        startChatAnnouncementService()
        observeConnectivity()
    }

    private fun observeConnectivity() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    private fun setWifiConnectionAvailable(isAvailable: Boolean) {
        viewModel.setWifiConnectionAvailable(isAvailable)
    }

    // TODO: Handle start and stop of service in [IntroductionScreen.kt] and [AvailableChatsScreen.kt]
    private fun startChatAnnouncementService() {
        val intent = Intent(this, ChatService::class.java)
        startService(intent)
    }
}
