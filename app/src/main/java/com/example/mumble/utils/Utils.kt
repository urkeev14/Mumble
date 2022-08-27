package com.example.mumble.utils

import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.ServerSocket
import java.net.SocketException
import java.util.Enumeration

/**
 * Get Local IPv4 address
 * @return address or null if local address not found
 */
suspend fun getIPv4Address(): String? {
    try {
        val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
        while (en.hasMoreElements()) {
            val `interface`: NetworkInterface = en.nextElement()
            val enumIpAddr: Enumeration<InetAddress> = `interface`.inetAddresses
            while (enumIpAddr.hasMoreElements()) {
                val inetAddress: InetAddress = enumIpAddr.nextElement()
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
        ex.printStackTrace()
    }
    return null
}

/**
 * Finds and returns first available port
 *
 * @return available port
 */
fun getAvailablePort() = ServerSocket(0).localPort
