package com.example.mumble.ui.screens.chats.chat

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mumble.data.fake.fakeMessages
import com.example.mumble.data.fake.fakeUsers
import com.example.mumble.ui.model.Message
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceM
import com.example.mumble.ui.theme.spaceS

@Composable
fun MyMessageItem(modifier: Modifier = Modifier, message: Message) {

    val cut = 8.dp
    val screenUsagePercentage = .8f
    val shape = RoundedCornerShape(
        topStart = cut,
        topEnd = cut,
        bottomStart = cut,
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(screenUsagePercentage),
            horizontalAlignment = Alignment.End
        ) {
            Card(
                modifier = Modifier
                    .clip(shape)
                    .defaultMinSize(minWidth = 90.dp)
            ) {
                Column(modifier = Modifier.padding(spaceS)) {
                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = message.content,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.size(height = spaceM, width = 0.dp))
                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        textAlign = TextAlign.End,
                        text = message.time,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun PreviewCurrentUserMessage() {
    MumbleTheme {
        Surface {
            MessageItem(
                user = fakeUsers.first(),
                message = fakeMessages.first()
            )
        }
    }
}
