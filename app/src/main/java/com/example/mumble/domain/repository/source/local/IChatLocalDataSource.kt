package com.example.mumble.domain.repository.source.local

import com.example.mumble.domain.model.ConversationEntity
import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.model.OutgoingMessageEntity
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.services.ChatService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import java.util.UUID

interface IChatLocalDataSource {

    /**
     * Returns current users nickname flow
     *
     * @return users nickname
     */
    fun getCurrentUser(): Flow<UserEntity>

    /**
     * Returns all online users
     *
     * @return flow of list of [UserEntity]
     */
    fun getAllUsers(): Flow<List<UserEntity>>

    /**
     * Returns list of users that correspond to [ids]
     *
     * @param ids list of [UUID]
     */
    fun getUsers(ids: List<UUID>): Flow<List<UserEntity>>

    /**
     * Returns a user by id
     *
     * @param id id of a user
     * @return user by id
     */
    fun getUser(id: UUID): Flow<UserEntity>

    /**
     * Returns a list of all conversations
     *
     * @return list of [ConversationEntity]
     */
    fun getAllConversations(): Flow<List<ConversationEntity>>

    /**
     * Returns a conversation between particular users
     *
     * @param users users that the current user is chatting with
     * @return conversation between particular users
     */
    fun getConversation(users: List<UUID>): Flow<ConversationEntity?>

    /**
     * Returns the conversation by id
     *
     * @param id conversation identification
     * @return conversation by id
     */
    fun getConversation(id: UUID): Flow<ConversationEntity?>

    /**
     * Return a message to be sent
     *
     * @return pair of user and message to be sent to that user
     */
    fun getOutgoingMessage(): SharedFlow<OutgoingMessageEntity>

    /**
     * Return a message to be received
     *
     * @return pair of user and message to be sent to that user
     */
    fun getIncomingMessage(): SharedFlow<MessageEntity>

    /**
     * Updates current users nickname that he/she will be using while chatting
     *
     * @param newNickname a new nickname for the user
     */
    fun updateCurrentUsersNickname(newNickname: String)

    /**
     * Updates current user is visible/invisible to other users online
     *
     * @param online parameter that changes once NSD announcement is registered/unregistered
     */
    fun updateCurrentUserOnline(online: Boolean)

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
     * Updates message received my [MessageEntity.creatorId]
     *
     * @param messageEntity received message
     */
    suspend fun updateIncomingMessage(messageEntity: MessageEntity)

    /**
     * Updates conversation with a new message
     *
     * @param conversationId message of particular user
     * @param usersInConversation users in conversation
     * @param message message going into conversation
     */
    fun updateConversation(
        conversationId: UUID? = null,
        usersInConversation: List<UUID>,
        message: MessageEntity
    )

    /**
     * Saves discovered user found via [ChatService]
     *
     * @param userEntity
     */
    fun createNewUser(userEntity: UserEntity)

    /**
     * TODO
     *
     * @param participants
     */
    fun createConversation(participants: List<UUID>): UUID

    /**
     * Delete specific user by nickname
     *
     * @param id users identifier
     */
    fun deleteUser(id: UUID)
}
