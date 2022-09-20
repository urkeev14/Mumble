package com.example.mumble.ui.screens.chats.available

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mumble.data.fake.fakeUsers
import com.example.mumble.ui.model.User
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceS

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AvailableUsersList(
    list: List<User>,
    modifier: Modifier = Modifier,
    onStartChat: (User) -> Unit = {},
) {
    LazyColumn(modifier = modifier) {
        items(items = list, key = { it.id }) { user ->
            Spacer(modifier = Modifier.size(8.dp))
            AvailableUser(modifier = Modifier.animateItemPlacement(), user = user) {
                onStartChat(
                    user
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            if (list.indexOf(user) != list.lastIndex)
                Divider(thickness = 0.5.dp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewUsers() {
    MumbleTheme {
        Surface {
            AvailableUsersList(fakeUsers, Modifier.padding(spaceS))
        }
    }
}
