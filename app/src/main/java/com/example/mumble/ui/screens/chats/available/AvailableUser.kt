package com.example.mumble.ui.screens.chats.available

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.mumble.data.fake.fakeUsers
import com.example.mumble.ui.components.AvatarItem
import com.example.mumble.ui.model.User
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceM

@Composable
fun AvailableUser(
    user: User,
    modifier: Modifier = Modifier,
    onStartChat: (User) -> Unit = {}
) {
    ConstraintLayout(
        userItemConstraintSet,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onStartChat(user)
            }
    ) {
        AvatarItem(
            avatar = user.avatar,
            modifier = Modifier.layoutId("avatar")
        )
        Text(
            text = user.username,
            modifier = Modifier
                .layoutId("text")
                .padding(start = spaceM)
        )

        IconButton(
            modifier = Modifier.layoutId("icon"),
            onClick = { onStartChat(user) }
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Icon"
            )
        }
    }
}

private val userItemConstraintSet = ConstraintSet {
    val avatar = createRefFor("avatar")
    val text = createRefFor("text")
    val icon = createRefFor("icon")

    constrain(avatar) {
        start.linkTo(parent.start)
        centerVerticallyTo(parent)
    }
    constrain(text) {
        start.linkTo(avatar.end)
        centerVerticallyTo(parent)
    }
    constrain(icon) {
        end.linkTo(parent.end)
        centerVerticallyTo(parent)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserItem() {
    MumbleTheme(darkTheme = true) {
        Surface {
            AvailableUser(fakeUsers.first())
        }
    }
}
