package com.example.mumble.data.repository.source.local.impl

import com.example.mumble.data.repository.source.local.ChatLocalDataSource
import com.example.mumble.domain.model.ConversationEntity
import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
internal class ChatLocalDataSourceTest {

    private val unconfinedTestDispatcher = UnconfinedTestDispatcher()
    val conversationId = UUID.randomUUID()
    private val userEntity1 = UserEntity(
        username = RANDOM_USERNAME_1,
        isOnline = ONLINE,
        host = RANDOM_IP_1,
        port = RANDOM_PORT_1
    )
    private val userEntity2 = UserEntity(
        username = RANDOM_USERNAME_2,
        isOnline = OFFLINE,
        host = RANDOM_IP_2,
        port = RANDOM_PORT_2
    )
    private val messageEntity1 =
        MessageEntity(
            conversationId = conversationId,
            content = "text1",
            creatorId = userEntity1.id,
        )
    private val messageEntity2 =
        MessageEntity(
            conversationId = conversationId,
            content = "text2",
            creatorId = userEntity2.id,
        )
    private val conversation = ConversationEntity(
        id = conversationId,
        participants = listOf(userEntity1.id, userEntity2.id),
        messages = listOf(messageEntity1, messageEntity2)
    )

    private lateinit var sut: ChatLocalDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(unconfinedTestDispatcher)
        sut = ChatLocalDataSource(
            MutableStateFlow(UserEntity()),
            MutableStateFlow(listOf()),
            MutableStateFlow(listOf(userEntity1, userEntity2)),
            MutableSharedFlow(),
            MutableSharedFlow()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCurrentUser returns empty user when user not updated`() = runBlocking {
        val actual = sut.getCurrentUser().first()
        assertTrue(
            actual.username.isEmpty() &&
                actual.host.isEmpty() &&
                actual.port == -1 &&
                actual.isOnline.not() &&
                actual.isReadyToChat.not()
        )
    }

    @Test
    fun `getCurrentUser returns updated user when user is updated`() = runBlocking {
        val expected = userEntity1
        sut.updateCurrentUser(expected)

        val actual = sut.getCurrentUser().first()
        assertEquals(expected, actual)
    }

    @Test
    fun `getAllUsers returns all users from conversations`() = runBlocking {
        // When creating 2 new users (one of them created twice)
        sut.createNewUser(userEntity1)
        sut.createNewUser(userEntity2)
        sut.createNewUser(userEntity2)

        // When we try to get the list of all users
        val expected = listOf(userEntity1, userEntity2)
        val actual = sut.getAllUsers().first()

        // Then the user list contains only first two inserted users
        assertEquals(expected, actual)
    }

    @Test
    fun `getUsers returns users by ID`() = runBlocking {
        // Given initial list of users
        // When trying to fetch userEntity1 by its ID
        val expected = listOf(userEntity1)
        val actual = sut.getUsers(listOf(userEntity1.id)).first()
        // Then the list with online userEntity1 is returned
        assertEquals(expected, actual)
    }

    @Test
    fun `getUser returns user by ID`() = runBlocking {
        // Given initial list of users
        // When trying to fetch userEntity1 by its ID
        val expected = userEntity1
        val actual = sut.getUser(userEntity1.id).first()

        // Then userEntity1 is returned
        assertEquals(expected, actual)
    }

    @Test
    fun `createNewUser saves new users`() = runBlocking {
        // Given that user1 and user2are created
        sut.createNewUser(userEntity1)
        sut.createNewUser(userEntity2)

        // When we try to fetch user list
        val actual = sut.getAllUsers().first()

        // Then the list will contain both user1 and user2
        assertTrue(actual.containsAll(listOf(userEntity1, userEntity2)))
    }

    @Test
    fun `createNewUser saves only users with distinct ids`() = runBlocking {
        // Given two users with same username

        // When both users are created
        sut.createNewUser(userEntity1)
        sut.createNewUser(userEntity1)
        val actual = sut.getAllUsers().first()

        // Then only the second user will exist
        // because there should be no 2 users with same username
        assertTrue(actual.count { it.id == userEntity1.id } == 1)
    }

    @Test
    fun `updateCurrentUser updates current user`() = runBlocking {
        // Given the initial user data
        sut.updateCurrentUser(userEntity1)

        // When updateCurrentUser method is called
        val updatedUser = userEntity1.copy(username = RANDOM_USERNAME_2)
        sut.updateCurrentUser(updatedUser)

        // Then user gets updated successfully
        val actual = sut.getCurrentUser().first()
        assertEquals(actual, updatedUser)
    }

    @Test
    fun `updateConversations updates particular conversation with message`() = runBlocking {
        // Given a new message arrived
        // When the conversation is updated
        sut.updateConversation(
            conversationId,
            listOf(userEntity1.id, userEntity2.id),
            messageEntity1
        )

        val expected = listOf(messageEntity1)
        val actual = sut.getConversation(conversationId).first()?.messages?.firstOrNull()
        // Then the conversation will contain this messages' ID
        assertEquals(expected.first().conversationId, actual?.id)
    }

    @Test
    fun `updateOutgoingMessage updates message to be sent`() = runBlocking {
        // TODO
    }

    @Test
    fun `updateIncomingMessage updates received message`() = runBlocking {
        val actual = mutableListOf<MessageEntity>()
        // When we try to read the last given message
        val job = launch(unconfinedTestDispatcher) {
            sut.getIncomingMessage().toList(actual)
        }
        // Given that user receive messages from user1 and user2 respectively
        sut.updateIncomingMessage(messageEntity1)
        sut.updateIncomingMessage(messageEntity2)
        val expected = messageEntity2

        // Then that message will be from user2
        assertEquals(expected, actual.drop(1).first())
        job.cancel()
    }

    @Test
    fun `deleteUser deletes user`() = runBlocking {
        // Given that 2 new users were created
        sut.createNewUser(userEntity1)
        sut.createNewUser(userEntity2)

        // When deleteUser method is called for user1
        sut.deleteUser(userEntity1.id)

        // Then users list does not contain user1
        val actual = sut.getAllUsers().first()
        assertFalse(actual.contains(userEntity1))
    }

    @Test
    fun `updateCurrentUsersNickname updates current users nickname successfully`() = runBlocking {
        // Given the initial user data
        sut.updateCurrentUsersNickname(RANDOM_USERNAME_1)

        // Then users nickname gets updated
        val expected = RANDOM_USERNAME_1
        val actual = sut.getCurrentUser().first().username
        assertTrue(actual == expected)
    }

    @Test
    fun `updateCurrentUserOnline updates user online status to true successfully`() = runBlocking {
        // Given the initial offline user
        sut.updateCurrentUser(userEntity2)

        // Whenever use case is invoked to set users online status to true
        sut.updateCurrentUserOnline(ONLINE)

        val expected = ONLINE
        val actual = sut.getCurrentUser().first().isOnline

        // The online status is set to false
        assertEquals(expected, actual)
    }

    @Test
    fun `updateCurrentUserOnline sets user online status to false successfully`() = runBlocking {
        // Given the initial online user
        sut.updateCurrentUser(userEntity1)

        // Whenever use case is invoked to set users online status to false
        sut.updateCurrentUserOnline(OFFLINE)

        val expected = OFFLINE
        val actual = sut.getCurrentUser().first().isOnline

        // The online status is set to false
        assertEquals(expected, actual)
    }

    companion object {
        const val ONLINE = true
        const val OFFLINE = false
        const val RANDOM_USERNAME_1 = "urkeev14"
        const val RANDOM_USERNAME_2 = "urkeev2323"
        const val RANDOM_IP_1 = "192.168.0.1"
        const val RANDOM_IP_2 = "192.168.0.3"
        const val RANDOM_PORT_1 = 1234
        const val RANDOM_PORT_2 = 2345
    }
}
