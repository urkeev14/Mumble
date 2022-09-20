package com.example.mumble.domain.dto

import java.util.UUID

data class UserDto(
    val id: UUID,
    val host: String,
    val port: Int
)
