package com.example.mumble.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.ServerSocket
import java.net.SocketException
import java.util.Enumeration

private val TAG = "Utils"

/**
 * Get Local IPv4 address
 * @return address or null if local address not found
 */
suspend fun getIPv4Address(): String? = withContext(Dispatchers.IO) {
    try {
        val networkInterfaces: Enumeration<NetworkInterface> =
            NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            val inetAddresses = networkInterface.inetAddresses
            while (inetAddresses.hasMoreElements()) {
                val inetAddress = inetAddresses.nextElement()
                if (
                    !inetAddress.isLoopbackAddress &&
                    inetAddress is Inet4Address &&
                    inetAddress.hostAddress?.contains("192.168") == true
                ) {
                    inetAddress.hostName
                }
            }
        }
    } catch (ex: SocketException) {
        Log.e(TAG, "getIPv4Address: ", ex)
    }
    null
}

/**
 * Finds and returns first available port
 *
 * @return available port
 */
fun getAvailablePort(): Int {
    val socket = ServerSocket(0)
    socket.close()
    return socket.localPort
}
