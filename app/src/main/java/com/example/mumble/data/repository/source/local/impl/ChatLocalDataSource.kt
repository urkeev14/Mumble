package com.example.mumble.data.repository.source.local.impl

import com.example.mumble.data.repository.source.local.IChatLocalDataSource
import com.example.mumble.domain.model.User
import com.example.mumble.utils.OtherUsersOnline
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update

class ChatLocalDataSource : IChatLocalDataSource {

    private val currentUser: MutableStateFlow<User> = MutableStateFlow(User())
    private val usersOnline: MutableStateFlow<OtherUsersOnline> = MutableStateFlow(emptyList())

    override fun getCurrentUser(): Flow<User> {
        return currentUser.asStateFlow()
    }

    override suspend fun createNewUser(user: User) {
        usersOnline.update { users ->
            users.filter { it.username != user.username } + user
        }
    }

    override suspend fun getAllUsers(): Flow<OtherUsersOnline> {
        return usersOnline.asStateFlow()
    }

    override suspend fun updateCurrentUser(user: User) {
        currentUser.update { user.copy() }
    }

    override fun deleteUser(nickname: String) {
        usersOnline.update { users ->
            users.filterNot { it.username != nickname }
        }
    }

    override suspend fun setCurrentUserOnline(isOnline: Boolean) {
        currentUser.update { it.copy(isOnline = isOnline) }
    }

    override suspend fun setCurrentUsersNickname(newNickname: String) {
        currentUser.update { it.copy(username = newNickname) }
    }
}
