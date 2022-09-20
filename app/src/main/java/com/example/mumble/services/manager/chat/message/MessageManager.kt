package com.example.mumble.services.manager.chat.message

import android.util.Log
import com.example.mumble.domain.dto.MessageDto
import com.example.mumble.domain.usecase.DeleteUserUseCase
import com.example.mumble.domain.usecase.HandleIncomingMessageUseCase
import com.example.mumble.domain.usecase.HandleSentMessageUseCase
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.util.UUID
import javax.inject.Inject

class MessageManager(
    private val userId: UUID,
    private val socket: Socket
) : IMessageManager {

    @Inject
    lateinit var handleIncomingMessageUseCase: HandleIncomingMessageUseCase

    @Inject
    lateinit var handleOutgoingMessageUseCase: HandleSentMessageUseCase

    @Inject
    lateinit var deleteUserUseCase: DeleteUserUseCase

    private val outStream: OutputStream = socket.getOutputStream()
    private val inStream: InputStream = socket.getInputStream()

    init {
        handleIncomingMessages()
    }

    private fun handleIncomingMessages() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val message = Gson().fromJson(readMessage(), MessageDto::class.java)
                handleIncomingMessageUseCase(message)
                Log.d(TAG, "Message received: $message")
            }
        }
    }

    private fun readMessage(): String {
        val data = ByteArray(1560)
        var readBytes: Int
        val output = ByteArrayOutputStream()
        try {
            do {
                readBytes = inStream.read(data)
                output.write(data, 0, readBytes)
                if (readBytes < data.size) break
            } while (readBytes != -1 || readBytes != 0)
        } catch (e: IOException) {
            deleteUserUseCase(userId)
            Log.d(TAG, "readMessage: ${e.message}")
        } catch (e: Exception) {
            Log.d(TAG, "readMessage: ${e.message}")
        }
        return String(output.toByteArray())
    }

    override suspend fun send(message: MessageDto) = withContext(Dispatchers.IO) {
        val input = Gson().toJson(message)
        outStream.write(input.toByteArray())
        handleOutgoingMessageUseCase(message)
    }

    override fun isThisUsersManager(id: UUID): Boolean {
        return userId == id
    }

    companion object {
        const val TAG = "MessageManager"
    }
}
