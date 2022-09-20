package com.example.mumble.domain.dto

data class MessageWithUserDto(
    val outgoingMessageDto: OutgoingMessageDto,
    val recepientDto: UserDto
)
