package com.example.mumble.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.mumble.domain.model.User
import com.example.mumble.ui.theme.MumbleTheme

@Composable
fun UserItem(
    user: User,
    modifier: Modifier = Modifier,
    onStartChat: (User) -> Unit = {}
) {
    ConstraintLayout(
        userItemConstraintSet,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
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
                .padding(start = 16.dp)
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
    MumbleTheme {
        UserItem(User(username = "urkeev1414"))
    }
}
