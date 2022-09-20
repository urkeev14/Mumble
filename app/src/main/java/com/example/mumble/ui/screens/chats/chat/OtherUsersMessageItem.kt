package com.example.mumble.ui.screens.chats.chat

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mumble.data.fake.fakeMessages
import com.example.mumble.data.fake.fakeUsers
import com.example.mumble.ui.components.AvatarItem
import com.example.mumble.ui.model.Message
import com.example.mumble.ui.model.User
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceS

@Composable
fun OtherUsersMessageItem(
    modifier: Modifier = Modifier,
    user: User,
    message: Message
) {

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val backgroundColor = user.avatar.color.copy(alpha = .5f)
    val cut = 8.dp
    val screenUsagePercentage = .8f
    val shape = RoundedCornerShape(
        topStart = cut,
        topEnd = cut,
        bottomEnd = cut,
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        AvatarItem(avatar = user.avatar, size = 40.dp)
        Spacer(modifier = Modifier.size(width = spaceS, height = 0.dp))

        Card(
            modifier = Modifier
                .clip(shape)
                .widthIn(90.dp, screenWidth * screenUsagePercentage),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
                contentColor = contentColorFor(backgroundColor = backgroundColor)
            )
        ) {
            Column(modifier = Modifier.padding(spaceS)) {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.size(height = spaceS, width = 0.dp))
                Text(
                    textAlign = TextAlign.End,
                    text = message.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColorFor(backgroundColor)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun PreviewOtherUsersMessage() {
    MumbleTheme {
        Surface {
            MessageItem(
                user = fakeUsers[1],
                message = fakeMessages.first()
            )
        }
    }
}
