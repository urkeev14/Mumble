package com.example.mumble.ui.screens.chats.conversations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mumble.R
import com.example.mumble.data.fake.fakeConversations
import com.example.mumble.domain.model.ToolbarConfiguration
import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.ui.components.Search
import com.example.mumble.ui.model.Conversation
import com.example.mumble.ui.navigation.Screen
import com.example.mumble.ui.screens.chats.available.EmptyScreen
import com.example.mumble.ui.tags.ChatsScreenTags
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceS
import com.example.mumble.ui.utils.navigate
import java.util.UUID

@Composable
fun ConversationsScreen(
    modifier: Modifier = Modifier,
    viewModel: ConversationsViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
) {
    viewModel.updateUiConfiguration(
        UiConfiguration(
            ToolbarConfiguration(
                LocalContext.current.resources.getString(
                    R.string.lets_chat
                ),
                screen = Screen.Chats.Conversations
            )
        )
    )
    val conversations by viewModel.conversations.collectAsState()
    val search by viewModel.search.collectAsState()

    ConversationsContent(
        modifier = modifier,
        search = search,
        onSearchChange = viewModel::setSearch,
        conversations = conversations,
        onConversationClick = {
            navController.navigate(Screen.Chat(it.toString()))
        }
    )
}

@Composable
private fun ConversationsContent(
    modifier: Modifier = Modifier,
    search: String,
    onSearchChange: (String) -> Unit,
    conversations: List<Conversation>,
    onConversationClick: (UUID) -> Unit
) {
    if (conversations.isEmpty()) {
        EmptyScreen(
            iconRedId = R.drawable.ic_empty_inbox,
            titleResId = R.string.ic_empty_inbox_title,
            descriptionResId = R.string.ic_empty_inbox_description
        )
    } else {
        Column(
            modifier = modifier
                .testTag(ChatsScreenTags.ChatUsersWithSearch.toString())
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Search(
                value = search,
                modifier = Modifier
                    .testTag(ChatsScreenTags.Search.toString()),
                onValueChange = onSearchChange
            )
            ConversationList(conversations = conversations, onConversationClick = {
                val id = it.messages.first { message ->
                    message.creator.isCurrentUser.not()
                }.creator.id
                onConversationClick(id)
            })
        }
    }
}

@Composable
private fun ConversationList(
    modifier: Modifier = Modifier,
    conversations: List<Conversation>,
    onConversationClick: (Conversation) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = conversations, key = { it.id }) { conversation ->
            Spacer(modifier = Modifier.size(8.dp))
            ConversationItem(conversation = conversation, onClick = onConversationClick)
            Spacer(modifier = Modifier.size(8.dp))
            if (conversations.last() != conversation) Divider(thickness = 0.5.dp)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewConversationList() {
    MumbleTheme(darkTheme = false) {
        ConversationsContent(
            modifier = Modifier.padding(spaceS),
            search = "",
            onSearchChange = {},
            fakeConversations,
            {}
        )
    }
}
