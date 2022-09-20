package com.example.mumble.ui.screens.chats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mumble.R
import com.example.mumble.domain.model.ToolbarConfiguration
import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.domain.model.User
import com.example.mumble.ui.components.Search
import com.example.mumble.ui.components.Users
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.utils.OtherUsersOnline
import com.example.mumble.utils.extensions.usingContext

@Composable
fun ChatsScreen(
    paddingValues: PaddingValues,
    viewModel: ChatsViewModel = hiltViewModel(),
    onStartChat: (User) -> Unit = {}
) {
    // TODO: Implement sending a flag to repository flowable that triggers START action of ChatAnnouncementService
    usingContext {
        viewModel.updateUiConfiguration(
            UiConfiguration(
                ToolbarConfiguration(
                    it.resources.getString(
                        R.string.chats
                    )
                )
            )
        )
    }
    val users by viewModel.usersOnline.collectAsState()
    val search by viewModel.search.collectAsState()

    ChatsScreenContent(
        paddingValues = paddingValues,
        users = users,
        search = search,
        viewModel::setSearch,
        onStartChat::invoke
    )
}

@Composable
private fun ChatsScreenContent(
    paddingValues: PaddingValues = PaddingValues(),
    users: OtherUsersOnline,
    search: String,
    onSearchFilterChange: ((String) -> Unit) = {},
    onStartChat: ((User) -> Unit) = {},
) {
    if (users.isEmpty()) {
        NobodyOnlineScreen()
    } else {
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Search(
                value = search,
                modifier = Modifier.padding(8.dp),
                onValueChange = onSearchFilterChange::invoke
            )
            Users(list = users, modifier = Modifier.padding(8.dp)) {
                onStartChat(it)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewChatScreen() {
    MumbleTheme {
        ChatsScreenContent(users = emptyList(), search = "urkeev")
    }
}
