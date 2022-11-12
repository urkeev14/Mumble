package com.example.mumble.ui.screens.chat

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mumble.data.fake.fakeConversations
import com.example.mumble.domain.model.ToolbarState
import com.example.mumble.domain.model.UiState
import com.example.mumble.ui.components.MessageInputField
import com.example.mumble.ui.model.Conversation
import com.example.mumble.ui.model.User
import com.example.mumble.ui.navigation.Screen
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceS
import com.example.mumble.utils.extensions.usingContext
import java.util.UUID

@Composable
fun ChatScreen(
    userId: UUID,
    viewModel: ChatViewModel = hiltViewModel()
) {
    viewModel.loadConversation(userId)

    val conversation by viewModel.conversation.collectAsState()
    val messageContent by viewModel.messageContent.collectAsState()
    val user = conversation?.getParticipants()?.first { it.isCurrentUser.not() }

    user?.let {
        val configuration = getUiConfiguration(it)
        usingContext { viewModel.updateUiState(configuration) }
    }
    conversation?.let {
        ChatScreenContent(
            it,
            messageContent
        ) { content -> viewModel.setMessageContent(content) }
    }
}

@Composable
fun ChatScreenContent(
    conversation: Conversation,
    messageContent: String,
    onMessageContentChange: (String) -> Unit
) {
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
        ) {
            items(items = conversation.messages, key = { it.id }) { message ->
                MessageItem(user = message.creator, message = message)
                Spacer(modifier = Modifier.height(spaceS))
            }
        }
        MessageInputField(
            modifier = Modifier,
            value = messageContent,
            onValueChange = onMessageContentChange
        )
    }
}

@Composable
private fun getUiConfiguration(it: User): UiState {
    return UiState(
        ToolbarState(
            title = it.username,
            color = it.avatar.color,
            screen = Screen.Chat(it.id.toString())
        )
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewChatScreen() {
    MumbleTheme {
        Surface {
            ChatScreenContent(
                conversation = fakeConversations.first()
                    .copy(messages = fakeConversations.first().messages.take(10)),
                messageContent = "Hello there !",
                onMessageContentChange = {}
            )
        }
    }
}
