package com.example.mumble.data.repository.source.local.impl

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
        val expected = User("urkeev1414", ONLINE, "192.168.0.1", 1234)
        sut.updateCurrentUser(expected)

        val actual = sut.getCurrentUser().first()
        assertEquals(expected, actual)
    }

    @Test
    fun `createNewUser saves new users`() = runBlocking {
        val user1 = User("urkeev1414", ONLINE, "192.168.0.1", 1234)
        val user2 = User("urkeev2323", ONLINE, "192.168.0.1", 1234)

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
        val user1 = User("urkeev1414", ONLINE, "192.168.0.1", 1234)
        val user2 = User("urkeev1414", ONLINE, "192.168.0.3", 2345)

        sut.createNewUser(user1)
        sut.createNewUser(user2)
        val actual = sut.getAllUsers().first()

        assertTrue(actual.contains(user2))
        assertFalse(actual.contains(user1))
    }

    @Test
    fun `updateCurrentUser updates current user`() = runBlocking {
        // Given the initial user data
        val user = User("urkeev1414", ONLINE, "192.168.0.1", 1234)
        sut.updateCurrentUser(user)

        // When updateCurrentUser method is called
        val updatedUser = user.copy("other_username")
        sut.updateCurrentUser(updatedUser)

        // Then user gets updated successfully
        val actual = sut.getCurrentUser().first()
        assertEquals(actual, updatedUser)
    }

    @Test
    fun `deleteUser deletes user`() = runBlocking {
        // Given that 2 new users were created
        val user1 = User("urkeev1414", ONLINE, "192.168.0.1", 1234)
        val user2 = User("urkeev1414", ONLINE, "192.168.0.3", 2345)
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
        val user1 = User("urkeev1414", ONLINE, "192.168.0.1", 1234)
        sut.updateCurrentUser(user1)

        // When updating current users nickname
        val userWithUpdatedNickname = user1.copy("new_nickname")
        sut.setCurrentUsersNickname(userWithUpdatedNickname.username)

        // Then users nickname gets updated
        val actual = sut.getCurrentUser().first()
        assertTrue(actual.username == userWithUpdatedNickname.username)
    }

    @Test
    fun `setCurrentUserOnline sets user online status to true successfully`() = runBlocking {
        val user1 = User(
            username = "urkeev1414",
            host = "192.168.0.1",
            isOnline = OFFLINE,
            port = 1234
        )
        sut.updateCurrentUser(user1)

        var expectedIsOnline = false
        var actualIsOnline = sut.getCurrentUser().first().isOnline

        assertEquals(expectedIsOnline, actualIsOnline)

        sut.setCurrentUserOnline(true)

        expectedIsOnline = true
        actualIsOnline = sut.getCurrentUser().first().isOnline

        assertEquals(expectedIsOnline, actualIsOnline)
    }

    @Test
    fun `setCurrentUserOnline sets user online status to false successfully`() = runBlocking {
        // Given that user is online
        val user1 = User(
            username = "urkeev1414",
            isOnline = ONLINE,
            host = "192.168.0.1",
            port = 1234
        )
        sut.updateCurrentUser(user1)

        var expectedIsOnline = ONLINE
        var actualIsOnline = sut.getCurrentUser().first().isOnline

        assertEquals(expectedIsOnline, actualIsOnline)

        // Whenever use case is invoked to set users online status to false
        sut.setCurrentUserOnline(OFFLINE)

        expectedIsOnline = OFFLINE
        actualIsOnline = sut.getCurrentUser().first().isOnline

        // The online status is set to false
        assertEquals(expectedIsOnline, actualIsOnline)
    }

    companion object {
        const val ONLINE = true
        const val OFFLINE = false
    }
}
