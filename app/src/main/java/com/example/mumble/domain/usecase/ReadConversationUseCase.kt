package com.example.mumble.domain.usecase

import com.example.mumble.data.fake.fakeConversations
import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.ui.model.Conversation
import com.example.mumble.ui.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject

class ReadConversationUseCase @Inject constructor(
    private val repository: IChatRepository,
    private val messageMapper: Mapper<MessageWithUser, Message>,
) {

    operator fun invoke(usersIds: List<UUID>): Flow<Conversation?> {
        return flowOf(
            fakeConversations.first { conversation ->
                conversation.getParticipants().map { it.id }.containsAll(usersIds)
            }
        )
//        val currentUser = repository.getCurrentUser().first()
//        return repository.getConversation(usersIds.plus(currentUser.id)).map {
//            if (it == null) return@map null
//            val users = repository.getUsers(it.participants).first().plus(currentUser)
//            val messageWithUser = it.messages.map { message ->
//                val sender = users.first { user -> user.id == message.creatorId }
//                MessageWithUser(sender, message)
//            }
//            return@map Conversation(it.id, messageMapper.map(messageWithUser))
//        }
    }
}

data class MessageWithUser(
    val user: UserEntity,
    val message: MessageEntity
)
