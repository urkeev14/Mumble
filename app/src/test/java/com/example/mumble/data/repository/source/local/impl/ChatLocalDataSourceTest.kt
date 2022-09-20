package com.example.mumble.data.repository.source.local.impl

import com.example.mumble.data.repository.source.local.ChatLocalDataSource
import com.example.mumble.domain.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

internal class ChatLocalDataSourceTest {

    private val sut = ChatLocalDataSource()

    @Test
    fun `getCurrentUser returns empty user when user not updated`() = runBlocking {
        val expected = User()
        val actual = sut.getCurrentUser().first()
        assertEquals(expected, actual)
    }

    @Test
    fun `getCurrentUser returns updated user when user is updated`() = runBlocking {
        val expected = User(RANDOM_USERNAME_1, ONLINE, RANDOM_IP_1, 1234)
        sut.updateCurrentUser(expected)

        val actual = sut.getCurrentUser().first()
        assertEquals(expected, actual)
    }

    @Test
    fun `createNewUser saves new users`() = runBlocking {
        val user1 = User(RANDOM_USERNAME_1, ONLINE, RANDOM_IP_1, RANDOM_PORT_1)
        val user2 = User(RANDOM_USERNAME_2, ONLINE, RANDOM_IP_2, RANDOM_PORT_1)

        sut.createNewUser(user1)
        val actual1 = sut.getAllUsers().first()
        assertTrue(actual1.contains(user1))

        sut.createNewUser(user2)
        val actual2 = sut.getAllUsers().first()
        assertTrue(actual2.contains(user1))
        assertTrue(actual2.contains(user2))
    }

    @Test
    fun `createNewUser saves only users with distinct usernames`() = runBlocking {
        // Given two users with same username
        val user1 = User(RANDOM_USERNAME_1, ONLINE, RANDOM_IP_1, RANDOM_PORT_1)
        val user2 = User(RANDOM_USERNAME_1, ONLINE, RANDOM_IP_2, RANDOM_PORT_2)

        // When both users are created
        sut.createNewUser(user1)
        sut.createNewUser(user2)
        val actual = sut.getAllUsers().first()

        // Then only the second user will exist
        // because there should be no 2 users with same username
        assertTrue(actual.contains(user2))
        assertFalse(actual.contains(user1))
    }

    @Test
    fun `updateCurrentUser updates current user`() = runBlocking {
        // Given the initial user data
        val user = User(RANDOM_USERNAME_1, ONLINE, RANDOM_IP_1, RANDOM_PORT_1)
        sut.updateCurrentUser(user)

        // When updateCurrentUser method is called
        val updatedUser = user.copy(RANDOM_USERNAME_2)
        sut.updateCurrentUser(updatedUser)

        // Then user gets updated successfully
        val actual = sut.getCurrentUser().first()
        assertEquals(actual, updatedUser)
    }

    @Test
    fun `deleteUser deletes user`() = runBlocking {
        // Given that 2 new users were created
        val user1 = User(RANDOM_USERNAME_1, ONLINE, RANDOM_IP_1, RANDOM_PORT_1)
        val user2 = User(RANDOM_USERNAME_1, ONLINE, RANDOM_IP_2, RANDOM_PORT_2)
        sut.createNewUser(user1)
        sut.createNewUser(user2)

        // When deleteUser method is called for user1
        sut.deleteUser(user1.username)

        // Then users list does not contain user1
        val actual = sut.getAllUsers().first()
        assertFalse(actual.contains(user1))
    }

    @Test
    fun `setCurrentUsersNickname sets current users nickname successfully`() = runBlocking {
        // Given the initial user data
        val user1 = User(RANDOM_USERNAME_1)
        sut.updateCurrentUser(user1)

        // When updating current users nickname
        val userWithUpdatedNickname = user1.copy(RANDOM_USERNAME_2)
        sut.setCurrentUsersNickname(userWithUpdatedNickname.username)

        // Then users nickname gets updated
        val actual = sut.getCurrentUser().first()
        assertTrue(actual.username == userWithUpdatedNickname.username)
    }

    @Test
    fun `setCurrentUserOnline sets user online status to true successfully`() = runBlocking {
        // Whenever use case is invoked to set users online status to false
        sut.setCurrentUserOnline(ONLINE)

        val expected = ONLINE
        val actual = sut.getCurrentUser().first().isOnline

        // The online status is set to false
        assertEquals(expected, actual)
    }

    @Test
    fun `setCurrentUserOnline sets user online status to false successfully`() = runBlocking {
        // Given the initial user data
        val user1 = User(RANDOM_USERNAME_1)
        sut.updateCurrentUser(user1)

        // Whenever use case is invoked to set users online status to false
        sut.setCurrentUserOnline(OFFLINE)

        val expected = OFFLINE
        val user = sut.getCurrentUser().first()
        val actual = user.isOnline

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
