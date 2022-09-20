package com.example.mumble.data.fake

import com.example.mumble.ui.model.Conversation
import java.util.UUID

val fakeConversations = listOf(
    Conversation(id = UUID.randomUUID(), fakeMessages.shuffled()),
    Conversation(id = UUID.randomUUID(), fakeMessages.shuffled()),
    Conversation(id = UUID.randomUUID(), fakeMessages.shuffled()),
    Conversation(id = UUID.randomUUID(), fakeMessages.shuffled()),
    Conversation(id = UUID.randomUUID(), fakeMessages.shuffled())
)

operator fun <T> Iterable<T>.times(count: Int): List<T> = List(count) { this }.flatten()
