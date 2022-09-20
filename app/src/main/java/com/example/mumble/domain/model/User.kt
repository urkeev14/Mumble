package com.example.mumble.domain.model

import androidx.compose.ui.graphics.Color
import com.example.mumble.ui.theme.RandomColors

data class User(
    val username: String = "",
    val isOnline: Boolean = false,
    val host: String = "",
    val port: Int = -1,
    val avatar: Avatar = Avatar(username.firstOrNull().toString())
) {

    override fun equals(other: Any?): Boolean {
        return other is User &&
            other.username == username &&
            other.host == host &&
            other.port == port
    }
}

data class Avatar(
    val monogram: String = "",
    var color: Color = RandomColors.random()
)
