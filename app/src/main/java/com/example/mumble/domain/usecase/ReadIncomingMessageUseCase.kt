package com.example.mumble.domain.usecase

import com.example.mumble.data.fake.fakeConversations
import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.ui.model.Message
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReadIncomingMessageUseCase @Inject constructor(
    private val repository: IChatRepository,
    private val messageMapper: Mapper<MessageWithUser, Message>
) {

/*    operator fun invoke(): Flow<Message> {
        return repository.getIncomingMessage().map {
            val creator = repository.getUser(it.creatorId).first()
            messageMapper.map(MessageWithUser(creator, it))
        }
    }*/

    //    Uncomment when want to test notifications
    operator fun invoke() = flow {
        for (i in 0..10) {
            delay(5000)
            emit(fakeConversations.first().messages.random())
        }
    }
}
