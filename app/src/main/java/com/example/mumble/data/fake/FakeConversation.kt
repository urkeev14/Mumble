package com.example.mumble.data.fake

import com.example.mumble.ui.model.Conversation
import java.util.UUID

val fakeConversations = listOf(
    Conversation(
        id = UUID.randomUUID(),
        messages = fakeMessages.takeWhile { it.creator.id == user2Id || it.creator.id == currentUserId }
    ),
)

operator fun <T> Iterable<T>.times(count: Int): List<T> = List(count) { this }.flatten()
