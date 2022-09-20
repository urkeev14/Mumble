package com.example.mumble.services.manager.chat.impl

import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.domain.usecase.ReadIncomingMessageUseCase
import com.example.mumble.domain.usecase.ReadOutgoingMessageUseCase
import com.example.mumble.domain.usecase.ReadUserByIpAddressUseCase
import com.example.mumble.domain.usecase.UpdateCurrentUserUseCase
import com.example.mumble.services.manager.chat.IChatManager
import com.example.mumble.services.manager.chat.message.MessageManager
import com.example.mumble.ui.screens.chats.chat.notification.NotificationHandler
import com.example.mumble.utils.getAvailablePort
import com.example.mumble.utils.getIPv4Address
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketAddress
import java.util.UUID
import javax.inject.Inject

class ChatManager @Inject constructor(
    private val notificationHandler: NotificationHandler,
    private val readCurrentUserUseCase: ReadCurrentUserUseCase,
    private val readUserByIpAddressUseCase: ReadUserByIpAddressUseCase,
    private val readOutgoingMessageUseCase: ReadOutgoingMessageUseCase,
    private val readIncomingMessageUseCase: ReadIncomingMessageUseCase,
    private val updateCurrentUserUseCase: UpdateCurrentUserUseCase
) : IChatManager {

    private lateinit var server: ServerSocket
    private val messageManagers: MutableList<MessageManager> = mutableListOf()

    override fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            observeCurrentUserChanges()
        }
        CoroutineScope(Dispatchers.IO).launch {
            observeOutgoingMessages()
        }
        CoroutineScope(Dispatchers.IO).launch {
            observeIncomingMessages()
        }
    }

    private suspend fun observeOutgoingMessages() {
        readOutgoingMessageUseCase().collect { input ->
            for (recipient in input.recipients) {
                var manager = messageManagers.firstOrNull { it.isThisUsersManager(recipient.id) }
                if (manager != null) {
                    manager.send(input.message)
                    continue
                }
                withContext(Dispatchers.IO) {
                    val socket = Socket(InetAddress.getByName(recipient.host), recipient.port)
                    manager = createNewMessageManager(recipient.id, socket)
                    manager?.send(input.message)
                }
            }
        }
    }

    private suspend fun observeIncomingMessages() {
        readIncomingMessageUseCase().collect {
            notificationHandler.showNotification(it)
        }
    }

    private suspend fun observeCurrentUserChanges() {
        readCurrentUserUseCase().collect { currentUser ->
            withContext(Dispatchers.IO) {
                if (currentUser.isOnline) return@withContext
                server =
                    ServerSocket(
                        getAvailablePort(),
                        SERVER_SOCKET_BACKLOG,
                        InetAddress.getByName(getIPv4Address())
                    )
                delay(1000)
                val host = server.localSocketAddress.getHost()
                val port = server.localPort
                updateCurrentUserUseCase(currentUser.copy(host = host, port = port))

                while (true) {
                    val socket = server.accept()
                    val userHostAddress =
                        socket.remoteSocketAddress.getHost()
                    val user = readUserByIpAddressUseCase(userHostAddress) ?: continue

                    createNewMessageManager(user.id, socket)
                }
            }
        }
    }

    private fun createNewMessageManager(
        userId: UUID,
        socket: Socket,
    ): MessageManager {
        val manager = MessageManager(userId, socket)
        messageManagers.add(manager)
        return manager
    }

    companion object {
        const val SERVER_SOCKET_BACKLOG = 100
    }
}

fun SocketAddress.getHost() = this.toString().replace("/", "").split(":").first()
