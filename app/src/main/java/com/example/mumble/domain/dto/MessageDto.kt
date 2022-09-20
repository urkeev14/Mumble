package com.example.mumble.domain.dto

data class MessageDto(
    val id: String,
    val conversationId: String,
    val content: String,
    val creatorId: String,
    val recipientsIds: List<String>,
    val time: Long
)
