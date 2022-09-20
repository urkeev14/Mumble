package com.example.mumble.domain.model

data class MessageWithSenderEntity(
    val message: MessageEntity,
    val sender: UserEntity
)
