package com.example.mumble.data.repository.source.local

import com.example.mumble.data.fake.fakeUsers
import com.example.mumble.domain.model.User
import com.example.mumble.domain.repository.source.IChatLocalDataSource
import com.example.mumble.services.manager.UserDiscoveryManager
import com.example.mumble.utils.OtherUsersOnline
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatLocalDataSource : IChatLocalDataSource {

    /**
     * The very user using this application.
     */
    private val currentUser: MutableStateFlow<User> = MutableStateFlow(User())

    /**
     * All users found online via [UserDiscoveryManager]
     * List can take a value of [fakeUsers] when debugging.
     */
    private val usersOnline: MutableStateFlow<OtherUsersOnline> = MutableStateFlow(fakeUsers)

    override fun getCurrentUser(): Flow<User> {
        return currentUser.asStateFlow()
    }

    override suspend fun createNewUser(user: User) {
        usersOnline.update { users ->
            users.filter { it.username != user.username } + user
        }
    }

    override fun getAllUsers(): Flow<OtherUsersOnline> {
        return usersOnline.asStateFlow()
    }

    override suspend fun updateCurrentUser(user: User) {
        currentUser.update { user.copy() }
    }

    override fun deleteUser(nickname: String) {
        usersOnline.update { users ->
            users.filter { it.username != nickname }
        }
    }

    override suspend fun setCurrentUserOnline(isOnline: Boolean) {
        currentUser.update { it.copy(isOnline = isOnline) }
    }

    override suspend fun setCurrentUsersNickname(newNickname: String) {
        currentUser.update { it.copy(username = newNickname) }
    }
}
