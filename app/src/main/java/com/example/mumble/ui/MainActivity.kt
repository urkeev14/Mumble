package com.example.mumble.ui

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.mumble.services.ChatAnnouncementService
import com.example.mumble.ui.components.Logo
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.utils.extensions.observeIn
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
                MumbleNavHost()
            }
        }
        startChatAnnouncementService()
        observeConnectivity()
    }

    override fun onStart() {
        super.onStart()
        observeIn(lifecycleScope, viewModel.uiMessage) {
            Log.d("MainActivity", it.toString())
        }
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

    private fun startChatAnnouncementService() {
        val intent = Intent(this, ChatAnnouncementService::class.java)
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopChatAnnouncementService()
    }

    private fun stopChatAnnouncementService() {
        val intent = Intent(this, ChatAnnouncementService::class.java)
        stopService(intent)
    }
}

/*
@Preview
@Composable
private fun PreviewMainActivity() {
    MumbleTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            Text(text = "Let's have some fun", style = MaterialTheme.typography.body1)
        }
    }
}
*/