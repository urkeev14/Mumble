package com.example.mumble.data.repository.impl

import com.example.mumble.domain.model.ConversationEntity
import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.model.OutgoingMessageEntity
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.domain.repository.source.IChatLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import java.util.UUID
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val dataSource: IChatLocalDataSource
) : IChatRepository {

    override fun getCurrentUser(): Flow<UserEntity> {
        return dataSource.getCurrentUser()
    }

    override fun getAllUsers(): Flow<List<UserEntity>> {
        return dataSource.getAllUsers()
    }

    override fun getUsers(ids: List<UUID>): Flow<List<UserEntity>> {
        return dataSource.getUsers(ids)
    }

    override fun getUser(id: UUID): Flow<UserEntity> {
        return dataSource.getUser(id)
    }

    override fun getConversations(): Flow<List<ConversationEntity>> {
        return dataSource.getAllConversations()
    }

    override fun getConversation(participants: List<UUID>): Flow<ConversationEntity?> {
        return dataSource.getConversation(participants)
    }

    override fun getConversation(id: UUID): Flow<ConversationEntity?> {
        return dataSource.getConversation(id)
    }

    override fun getOutgoingMessage(): SharedFlow<OutgoingMessageEntity> {
        return dataSource.getOutgoingMessage()
    }

    override fun getIncomingMessage(): SharedFlow<MessageEntity> {
        return dataSource.getIncomingMessage()
    }

    override fun getMessageToBeReceived(): SharedFlow<MessageEntity> {
        return dataSource.getIncomingMessage()
    }

    override fun updateCurrentUser(userEntity: UserEntity) {
        dataSource.updateCurrentUser(userEntity)
    }

    override suspend fun updateOutgoingMessage(messageEntity: OutgoingMessageEntity) {
        dataSource.updateOutgoingMessage(messageEntity)
    }

    override suspend fun updateIncomingMessage(messageEntity: MessageEntity) {
        dataSource.updateIncomingMessage(messageEntity)
    }

    override fun updateConversation(
        conversationId: UUID?,
        usersInConversation: List<UUID>,
        message: MessageEntity
    ) {
        dataSource.updateConversation(conversationId, usersInConversation, message)
    }

    override fun updateCurrentUsersNickname(newNickname: String) {
        dataSource.updateCurrentUsersNickname(newNickname)
    }

    override fun updateCurrentUserOnline(isOnline: Boolean) {
        dataSource.updateCurrentUserOnline(isOnline)
    }

    override fun createUser(userEntity: UserEntity) {
        dataSource.createNewUser(userEntity)
    }

    override fun createConversation(participants: List<UUID>): UUID {
        return dataSource.createConversation(participants)
    }

    override fun deleteUser(id: UUID) {
        dataSource.deleteUser(id)
    }
}
