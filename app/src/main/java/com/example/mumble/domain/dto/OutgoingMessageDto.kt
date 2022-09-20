package com.example.mumble.domain.dto

data class OutgoingMessageDto(
    val message: MessageDto,
    val recipients: List<UserDto>
)
