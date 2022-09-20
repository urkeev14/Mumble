package com.example.mumble.utils

import android.util.Log
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
suspend fun getIPv4Address(): String? {
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
                    return inetAddress.hostName
                }
            }
        }
    } catch (ex: SocketException) {
        Log.e(TAG, "getIPv4Address: ", ex)
    }
    return null
}

/**
 * Finds and returns first available port
 *
 * @return available port
 */
fun getAvailablePort() = ServerSocket(0).localPort
