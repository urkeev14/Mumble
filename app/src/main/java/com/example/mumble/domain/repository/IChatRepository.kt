package com.example.mumble.domain.repository

import com.example.mumble.domain.model.User
import com.example.mumble.utils.OtherUsersOnline
import kotlinx.coroutines.flow.Flow

interface IChatRepository {
    /**
     * Gets current users nickname flow
     *
     * @return users nickname
     */
    fun getCurrentUser(): Flow<User>

    /**
     * Updates attributes of current user
     *
     * @param user current user
     */
    suspend fun updateCurrentUser(user: User)

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
    suspend fun createUser(user: User)

    /**
     * Returns all online users
     *
     * @return flow of online users
     */
    fun getAllUsers(): Flow<OtherUsersOnline>

    /**
     * Delete existing user by nickname
     *
     * @param nickname nickname of a specific user
     */
    fun deleteUser(nickname: String)
}
