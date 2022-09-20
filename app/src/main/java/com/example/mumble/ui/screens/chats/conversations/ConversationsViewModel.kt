package com.example.mumble.ui.screens.chats.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mumble.domain.usecase.ReadAllConversationsUseCase
import com.example.mumble.ui.IUiManager
import com.example.mumble.ui.model.Conversation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val uiManager: IUiManager,
    readAllConversationsUseCase: ReadAllConversationsUseCase
) : ViewModel(), IUiManager by uiManager {

    private val _search: MutableStateFlow<String> = MutableStateFlow(EMPTY_SEARCH)
    val search = _search.asStateFlow()

    val conversations: StateFlow<List<Conversation>> = readAllConversationsUseCase()
        .combine(search) { conversations, search ->
            if (conversations.isEmpty()) {
                conversations
            } else {
                conversations
            }
        }.stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = listOf()
        )

    fun setSearch(value: String) {
        viewModelScope.launch {
            _search.emit(value)
        }
    }

    companion object {
        const val EMPTY_SEARCH = ""
    }
}
