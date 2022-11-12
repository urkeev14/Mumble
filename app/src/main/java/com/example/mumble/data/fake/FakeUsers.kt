package com.example.mumble.data.fake

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.mumble.ui.model.Avatar
import com.example.mumble.ui.model.Message
import com.example.mumble.ui.model.User
import com.example.mumble.ui.theme.RandomColors
import java.util.UUID

val currentUserId = UUID.randomUUID()
val user2Id = UUID.randomUUID()
val fakeUsers = listOf(
    User(
        id = currentUserId,
        username = "urkeev14",
        isCurrentUser = true,
        isOnline = false,
        avatar = Avatar(monogram = "u", color = RandomColors.random())
    ),
    User(
        id = user2Id,
        username = "marko1414414",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "m", color = RandomColors.random())
    ),
    User(
        id = UUID.randomUUID(),
        username = "nikola099",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "n", color = RandomColors.random())
    ),
    User(
        id = UUID.randomUUID(),
        username = "jovana967",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "j", color = RandomColors.random())
    ),
    User(
        id = UUID.randomUUID(),
        username = "milica33",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "m", color = RandomColors.random())
    ),
    User(
        id = UUID.randomUUID(),
        username = "jovana977",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "j", color = RandomColors.random())
    ),
    User(
        id = UUID.randomUUID(),
        username = "nikolina_markov",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "n", color = RandomColors.random())
    ),
    User(
        id = UUID.randomUUID(),
        username = "aleksandar_ra99",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "a", color = RandomColors.random())
    ),
    User(
        id = UUID.randomUUID(),
        username = "mirkostosic",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "m", color = RandomColors.random())
    ),
    User(
        id = UUID.randomUUID(),
        username = "mitarmiric",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "m", color = RandomColors.random())
    ),
    User(
        id = UUID.randomUUID(),
        username = "lola_lola2324",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "l", color = RandomColors.random())
    ),
    User(
        id = UUID.randomUUID(),
        username = "jovana_r43",
        isCurrentUser = false,
        isOnline = false,
        avatar = Avatar(monogram = "j", color = RandomColors.random())
    ),
)

val fakeMessages = getMessages()

fun getMessages(): List<Message> {
    val list = mutableListOf<Message>()
    for (i in 0..20) {
        list.add(
            Message(
                id = UUID.randomUUID(),
                content = LoremIpsum(i).values.joinToString(" "),
                time = "11:" + if (i < 10) "0$i" else i,
                creator = fakeUsers[(i + 1) % 2]
            )
        )
    }
    return list
}
