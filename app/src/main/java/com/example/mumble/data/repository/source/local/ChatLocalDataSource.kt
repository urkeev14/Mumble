package com.example.mumble.data.repository.source.local

import com.example.mumble.domain.model.ConversationEntity
import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.model.OutgoingMessageEntity
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.source.local.IChatLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.UUID

/**
 * Data source for all chat-specific data.
 *
 * @property currentUser the very user using this application.
 * @property conversations all users conversations in current session.
 * @property users all other users online
 * @property messages all messages received
 * @property outgoingMessage latest message that needs to be sent to [MessageEntity.recipientsIds]
 * @property incomingMessage latest message that was received from [MessageEntity.creatorId]
 */
class ChatLocalDataSource(
    private val currentUser: MutableStateFlow<UserEntity>,
    private val conversations: MutableStateFlow<List<ConversationEntity>>,
    private val users: MutableStateFlow<List<UserEntity>>,
    private val outgoingMessage: MutableSharedFlow<OutgoingMessageEntity>,
    private val incomingMessage: MutableSharedFlow<MessageEntity>
) : IChatLocalDataSource {

    override fun getCurrentUser(): Flow<UserEntity> {
        return currentUser.asStateFlow()
    }

    override fun getAllUsers(): Flow<List<UserEntity>> {
        return users
    }

    override fun getUsers(ids: List<UUID>): Flow<List<UserEntity>> {
        return users.map { list -> list.filter { ids.contains(it.id) } }
    }

    override fun getUser(id: UUID): Flow<UserEntity> {
        return users.map { list -> list.first { it.id == id } }
    }

    override fun getAllConversations(): Flow<List<ConversationEntity>> {
        return conversations.asStateFlow()
    }

    override fun getConversation(users: List<UUID>): Flow<ConversationEntity?> {
        return conversations.map { list ->
            list.firstOrNull { conversation ->
                conversation.isBetween(users)
            }
        }
    }

    override fun getConversation(id: UUID): Flow<ConversationEntity?> {
        return conversations.map { list ->
            list.firstOrNull { conversation ->
                conversation.id == id
            }
        }
    }

    override fun getOutgoingMessage(): SharedFlow<OutgoingMessageEntity> {
        return outgoingMessage.asSharedFlow()
    }

    override fun getIncomingMessage(): SharedFlow<MessageEntity> {
        return incomingMessage.asSharedFlow()
    }

    override fun updateCurrentUser(userEntity: UserEntity) {
        currentUser.update { userEntity }
    }

    override suspend fun updateOutgoingMessage(messageEntity: OutgoingMessageEntity) {
        outgoingMessage.emit(messageEntity)
    }

    override suspend fun updateIncomingMessage(messageEntity: MessageEntity) {
        incomingMessage.emit(messageEntity)
    }

    // TODO: Refactor. What happends if there is conversationId, but no conversation in list?
    override fun updateConversation(
        conversationId: UUID?,
        usersInConversation: List<UUID>,
        message: MessageEntity
    ) {
        conversations.update { list ->
            val conversationList = list.toMutableList()

            val conversation: ConversationEntity
            if (conversationId == null) {
                conversation = createNewConversation(UUID.randomUUID(), usersInConversation)
            } else {
                conversation = conversationList.firstOrNull {
                    it.id == conversationId
                } ?: createNewConversation(conversationId, usersInConversation)
                conversationList.remove(conversation)
            }

            conversationList.add(conversation.add(message.copy(conversationId = conversation.id)))
            conversationList.toList()
        }
    }

    private fun createNewConversation(id: UUID, usersInConversation: List<UUID>) =
        ConversationEntity(
            participants = usersInConversation,
        )

    override fun createNewUser(userEntity: UserEntity) {
        users.update {
            if (it.contains(userEntity))
                it
            else
                it.plus(userEntity)
        }
    }

    override fun createConversation(participants: List<UUID>): UUID {
        val newConversation =
            ConversationEntity(id = UUID.randomUUID(), participants = participants)
        conversations.update {
            it.plus(newConversation)
        }
        return newConversation.id
    }

    override fun deleteUser(id: UUID) {
        users.update { list ->
            list.filter { it.id != id }
        }
    }

    override fun updateCurrentUserOnline(online: Boolean) {
        currentUser.update { it.copy(isOnline = online) }
    }

    override fun updateCurrentUsersNickname(newNickname: String) {
        currentUser.update { it.copy(username = newNickname) }
    }
}
