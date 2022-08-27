package com.example.mumble.data.repository.source.local

import com.example.mumble.domain.model.User
import com.example.mumble.services.ChatDiscoveryService
import com.example.mumble.utils.OtherUsersOnline
import kotlinx.coroutines.flow.Flow

interface IChatLocalDataSource {

    /**
     * Gets current users nickname flow
     *
     * @return users nickname
     */
    fun getCurrentUser(): Flow<User>

    /**
     * Sets current users nickname that he/she will be using while chatting
     *
     * @param newNickname a new nickname for the user
     */
    suspend fun setCurrentUsersNickname(newNickname: String)

    /**
     * Set current user is visible/invisible to other users online
     *
     * @param isOnline parameter that changes once NSD announcement is registered/unregistered
     */
    suspend fun setCurrentUserOnline(isOnline: Boolean)

    /**
     * Saves discovered user found via [ChatDiscoveryService]
     *
     * @param user
     */
    suspend fun createNewUser(user: User)

    /**
     * Returns all online users
     *
     * @return flow of online users
     */
    suspend fun getAllUsers(): Flow<OtherUsersOnline>

    /**
     * Updates attributes of current user
     *
     * @param user current user
     */
    suspend fun updateCurrentUser(user: User)

    /**
     * Delete specific user by nickname
     *
     * @param nickname
     */
    fun deleteUser(nickname: String)
}
