package com.example.mumble.utils.extensions

import android.net.nsd.NsdServiceInfo
import com.example.mumble.services.ChatService

fun NsdServiceInfo.isFromMumbleApp(): Boolean {
    return serviceType.contains(ChatService.SERVICE_TYPE)
}
