package com.example.mumble.ui.screens.chats.conversations

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.mumble.data.fake.fakeConversations
import com.example.mumble.ui.components.AvatarItem
import com.example.mumble.ui.model.Conversation
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceS

@Composable
fun ConversationItem(
    modifier: Modifier = Modifier,
    conversation: Conversation,
    onClick: (Conversation) -> Unit
) {
    val lastMessage = conversation.messages.last()
    val user = lastMessage.creator
    ConstraintLayout(
        userItemConstraintSet,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClick(conversation)
            }
    ) {
        AvatarItem(
            avatar = user.avatar,
            modifier = Modifier.layoutId("avatar")
        )
        Text(
            modifier = Modifier
                .layoutId("username")
                .padding(start = spaceS),
            text = user.username,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .layoutId("content")
                .padding(start = spaceS),
            text = lastMessage.content,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier.layoutId("time"),
            text = lastMessage.time,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

private val userItemConstraintSet = ConstraintSet {
    val avatar = createRefFor("avatar")
    val username = createRefFor("username")
    val content = createRefFor("content")
    val time = createRefFor("time")

    constrain(avatar) {
        start.linkTo(parent.start)
        centerVerticallyTo(parent)
    }
    constrain(username) {
        top.linkTo(parent.top)
        start.linkTo(avatar.end)
        end.linkTo(time.start)
        width = Dimension.fillToConstraints
    }
    constrain(content) {
        start.linkTo(avatar.end)
        end.linkTo(parent.end)
        top.linkTo(username.bottom)
        width = Dimension.fillToConstraints
    }
    constrain(time) {
        end.linkTo(parent.end)
        top.linkTo(parent.top)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewConversationItem() {
    MumbleTheme {
        Surface {
            ConversationItem(
                modifier = Modifier,
                conversation = fakeConversations.last(),
                onClick = {}
            )
        }
    }
}
