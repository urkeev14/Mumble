package com.example.mumble.domain.repository

import com.example.mumble.domain.model.ConversationEntity
import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.model.OutgoingMessageEntity
import com.example.mumble.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import java.util.UUID

interface IChatRepository {

    /**
     * Gets current users nickname flow
     *
     * @return users nickname
     */
    fun getCurrentUser(): Flow<UserEntity>

    /**
     * Returns all online users
     *
     * @return flow of online users
     */
    fun getAllUsers(): Flow<List<UserEntity>>

    /**
     * Returns list of users that correspond to [ids]
     *
     * @param ids
     */
    fun getUsers(ids: List<UUID>): Flow<List<UserEntity>>

    /**
     * Return a user by id
     *
     * @param id id of a user
     * @return user by id
     */
    fun getUser(id: UUID): Flow<UserEntity>

    /**
     * Returns all conversations
     *
     * @return flow of list of [ConversationEntity]
     */
    fun getConversations(): Flow<List<ConversationEntity>>

    /**
     * Returns a conversation by its' participants
     *
     * @param participants users in the conversation
     * @return conversation with particular user
     */
    fun getConversation(participants: List<UUID>): Flow<ConversationEntity?>

    /**
     * Returns a conversation by id
     *
     * @param users users in the conversation
     * @return conversation with particular user
     */
    fun getConversation(id: UUID): Flow<ConversationEntity?>

    /**
     * Return a message to be sent
     *
     * @return message to be sent to [MessageEntity.recipientsIds]
     */
    fun getOutgoingMessage(): SharedFlow<OutgoingMessageEntity>

    /**
     * Return a newly received message
     *
     * @return newly received message
     */
    fun getIncomingMessage(): SharedFlow<MessageEntity>

    /**
     * Return a message to be received
     *
     * @return message to be received where
     * sender is [MessageEntity.creatorId] and receivers are [MessageEntity.recipientsIds]
     */
    fun getMessageToBeReceived(): SharedFlow<MessageEntity>

    /**
     * Sets current users nickname that he/she will be using while chatting
     *
     * @param newNickname a new nickname for the user
     */
    fun updateCurrentUsersNickname(newNickname: String)

    /**
     * Update current user is visible/invisible to other users online
     *
     * @param isOnline parameter that changes once NSD announcement is registered/unregistered
     */
    fun updateCurrentUserOnline(isOnline: Boolean)

    /**
     * Updates attributes of current user
     *
     * @param userEntity current user
     */
    fun updateCurrentUser(userEntity: UserEntity)

    /**
     * Updates message to be sent to some user
     *
     * @param messageEntity message to be sent
     */
    suspend fun updateOutgoingMessage(messageEntity: OutgoingMessageEntity)

    /**
     * Updates message to be received from some user
     *
     * @param messageEntity new message that is received
     */
    suspend fun updateIncomingMessage(messageEntity: MessageEntity)

    /**
     * Updates conversation map with message of particular user
     *
     * @param conversationId conversation identification
     * @param usersInConversation users in conversation
     * @param message message going into conversation
     */
    fun updateConversation(
        conversationId: UUID? = null,
        usersInConversation: List<UUID>,
        message: MessageEntity
    )

    /**
     * Saves discovered user found via [ChatDiscoveryService]
     *
     * @param userEntity new user
     */
    fun createUser(userEntity: UserEntity)

    /**
     * Creates an empty conversation
     *
     * @param participants participants in this conversation
     */
    fun createConversation(participants: List<UUID>): UUID

    /**
     * Delete existing user by nickname
     *
     * @param id users' identification
     */
    fun deleteUser(id: UUID)
}
