package com.example.mumble.services.manager.chat.message

import com.example.mumble.domain.dto.MessageDto
import java.util.UUID

interface IMessageManager {
    suspend fun send(message: MessageDto)
    fun isThisUsersManager(id: UUID): Boolean
}
