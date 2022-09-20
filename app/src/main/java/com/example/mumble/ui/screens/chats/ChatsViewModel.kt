package com.example.mumble.ui.screens.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mumble.domain.usecase.ReadAllUsersOnlineUseCase
import com.example.mumble.ui.IUiManager
import com.example.mumble.utils.OtherUsersOnline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val uiManager: IUiManager,
    readAllUsersOnlineUseCase: ReadAllUsersOnlineUseCase
) : ViewModel(), IUiManager by uiManager {

    private val _search: MutableStateFlow<String> = MutableStateFlow(EMPTY_SEARCH)
    val search = _search.asStateFlow()

    val usersOnline: StateFlow<OtherUsersOnline> = readAllUsersOnlineUseCase()
        .combine(search) { users, search ->
            users.filter { user -> user.username.contains(search) }
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
