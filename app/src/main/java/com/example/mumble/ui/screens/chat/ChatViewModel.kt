package com.example.mumble.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mumble.domain.model.User
import com.example.mumble.domain.usecase.ReadChatUserUseCase
import com.example.mumble.ui.IUiManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    uiManager: IUiManager,
    private val readChatUserUseCase: ReadChatUserUseCase
) : ViewModel(), IUiManager by uiManager {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user = _user.asStateFlow()

    fun loadUser(username: String) = viewModelScope.launch {
        val result = readChatUserUseCase(username).first()
        _user.value = result
    }
}
