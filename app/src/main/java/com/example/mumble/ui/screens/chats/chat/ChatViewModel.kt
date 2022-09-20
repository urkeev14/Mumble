package com.example.mumble.ui.screens.chats.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mumble.domain.usecase.ReadConversationUseCase
import com.example.mumble.domain.usecase.UpdateOutgoingMessageUseCase
import com.example.mumble.ui.IUiManager
import com.example.mumble.ui.model.Conversation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    uiManager: IUiManager,
    private val readConversationUseCase: ReadConversationUseCase,
    private val updateOutgoingMessageUseCase: UpdateOutgoingMessageUseCase
) : ViewModel(), IUiManager by uiManager {

    private val _conversation = MutableStateFlow<Conversation?>(null)
    val conversation = _conversation.asStateFlow()

    private val _messageContent = MutableStateFlow("")
    val messageContent = _messageContent.asStateFlow()

    fun loadConversation(user: UUID) {
        viewModelScope.launch {
            _conversation.value = readConversationUseCase(listOf(user)).first()
        }
    }

    fun setMessageContent(value: String) {
        _messageContent.value = value
    }

    fun sendMessage(content: String) = viewModelScope.launch {
        _conversation.value?.let {
            updateOutgoingMessageUseCase(
                it.id,
                it.getParticipants().map { participant -> participant.id },
                content
            )
        }
    }
}
