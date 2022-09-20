package com.example.mumble.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mumble.data.fake.fakeUsers
import com.example.mumble.domain.model.User
import com.example.mumble.ui.theme.MumbleTheme

@Composable
fun Users(
    list: List<User>,
    modifier: Modifier = Modifier,
    onStartChat: (User) -> Unit = {},
) {
    LazyColumn(modifier = modifier) {
        items(list) { user ->
            UserItem(user) { onStartChat(user) }
            Divider(thickness = 0.5.dp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewUsers() {
    MumbleTheme {
        Column {
            Users(fakeUsers, Modifier.padding(8.dp))
        }
    }
}
