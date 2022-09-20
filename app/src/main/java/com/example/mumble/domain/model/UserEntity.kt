package com.example.mumble.domain.model

import java.util.UUID

data class UserEntity(
    val id: UUID = UUID.randomUUID(),
    val username: String = "",
    val isCurrentUser: Boolean = false,
    val isOnline: Boolean = false,
    val host: String = "",
    val port: Int = -1,
    val avatar: AvatarEntity = AvatarEntity(username.firstOrNull().toString())
) {

    val isReadyToChat: Boolean
        get() = username.isNotEmpty() && host.isNotEmpty() && port != -1

    override fun toString(): String {
        return """
            User:
            | username -> $username
            | host     -> $host
            | port     -> $port
        """.trimIndent()
    }
}
