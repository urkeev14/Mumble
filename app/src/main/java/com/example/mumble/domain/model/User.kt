package com.example.mumble.domain.model

data class User(
    val username: String = "",
    val isOnline: Boolean = false,
    val host: String = "",
    val port: Int = -1
)
