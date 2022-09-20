package com.example.mumble.ui.screens.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mumble.domain.model.ToolbarConfiguration
import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.domain.model.User
import com.example.mumble.utils.extensions.usingContext

@Composable
fun ChatScreen(
    username: String,
    viewModel: ChatViewModel = hiltViewModel()
) {
    viewModel.loadUser(username)
    val user = viewModel.user.collectAsState()
    user.value?.let {
        val configuration = getUiConfiguration(username, it)
        usingContext { viewModel.updateUiConfiguration(configuration) }
    }
}

@Composable
private fun getUiConfiguration(username: String, it: User): UiConfiguration {
    return UiConfiguration(
        ToolbarConfiguration(
            title = username,
            isBackButtonVisible = true,
            color = it.avatar.color
        )
    )
}
