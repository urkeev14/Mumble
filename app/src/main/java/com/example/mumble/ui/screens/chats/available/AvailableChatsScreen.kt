package com.example.mumble.ui.screens.chats.available

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mumble.R
import com.example.mumble.data.fake.fakeUsers
import com.example.mumble.domain.model.ToolbarConfiguration
import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.ui.components.Search
import com.example.mumble.ui.model.User
import com.example.mumble.ui.navigation.Screen
import com.example.mumble.ui.tags.ChatsScreenTags
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceS
import com.example.mumble.ui.utils.navigate

@Composable
fun AvailableChatsScreen(
    viewModel: AvailableChatsViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
) {
    // TODO: Implement sending a flag to repository flowable that triggers START action of ChatAnnouncementService
    viewModel.updateUiConfiguration(
        UiConfiguration(
            ToolbarConfiguration(
                LocalContext.current.resources.getString(
                    R.string.lets_chat
                ),
                screen = Screen.Chats.AvailableUsers
            )
        )
    )
    val users by viewModel.usersOnline.collectAsState()
    val search by viewModel.search.collectAsState()

    AvailableChatsScreenContent(
        users = users, search = search, viewModel::setSearch
    ) {
        navController.navigate(Screen.Chat(it.id.toString()))
    }
}

@Composable
private fun AvailableChatsScreenContent(
    users: List<User>,
    search: String,
    onSearchFilterChange: ((String) -> Unit) = {},
    onStartChat: ((User) -> Unit) = {},
) {
    if (users.isEmpty()) {
        EmptyScreen(
            iconRedId = R.drawable.ic_alone,
            titleResId = R.string.nobody_online,
            descriptionResId = R.string.nobody_online_description
        )
    } else {
        AvailableChatsWithSearch(
            search = search,
            onSearchFilterChange = onSearchFilterChange,
            users = users,
            onStartChat = onStartChat
        )
    }
}

@Composable
private fun AvailableChatsWithSearch(
    modifier: Modifier = Modifier,
    search: String,
    onSearchFilterChange: (String) -> Unit,
    users: List<User>,
    onStartChat: (User) -> Unit
) {
    Column(
        modifier = modifier
            .testTag(ChatsScreenTags.ChatUsersWithSearch.toString())
            .padding(spaceS)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Search(
            value = search,
            modifier = Modifier.testTag(ChatsScreenTags.Search.toString()),
            onValueChange = onSearchFilterChange::invoke
        )
        AvailableUsersList(
            list = users,
            modifier = Modifier
                .testTag(ChatsScreenTags.Users.toString())
                .padding(spaceS)
        ) {
            onStartChat(it)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewAvailableUsersOnlineWithSearch() {
    MumbleTheme {
        Surface {
            AvailableChatsScreenContent(users = fakeUsers, search = "")
        }
    }
}
