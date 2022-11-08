package com.example.mumble.ui.screens.chats

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.mumble.data.fake.fakeUsers
import com.example.mumble.ui.MainActivity
import com.example.mumble.ui.screens.chats.available.AvailableChatsScreen
import com.example.mumble.ui.screens.chats.available.AvailableChatsViewModel
import com.example.mumble.ui.tags.ChatsScreenTags
import com.example.mumble.ui.theme.MumbleTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
internal class ChatsScreenTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    lateinit var viewModel: AvailableChatsViewModel
    lateinit var onStartChat: () -> Unit

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = mockk()

        onStartChat = mockk()
        every { onStartChat() } just Runs

        composeTestRule.activity.setContent {
            MumbleTheme {
                AvailableChatsScreen(PaddingValues(), viewModel) { onStartChat() }
            }
        }
    }

    @Test
    fun givenNoUsersOnline_whenLoadingScreen_thenNobodyOnlineScreenWillBeShown(): Unit =
        with(composeTestRule) {
            coEvery { viewModel.updateUiConfiguration(any()) } just Runs
            every { viewModel.search } returns MutableStateFlow("")
            every { viewModel.usersOnline } returns MutableStateFlow(emptyList())

            onNodeWithTag(ChatsScreenTags.InfoScreen.toString()).assertExists()
                .assertIsDisplayed()
            onNodeWithTag(ChatsScreenTags.ChatUsersWithSearch.toString()).assertDoesNotExist()
        }

    @Test
    fun givenFakeUsersOnline_whenLoadingScreen_thenNobodyOnlineScreenWillNotBeShown(): Unit =
        with(composeTestRule) {
            coEvery { viewModel.updateUiConfiguration(any()) } just Runs
            every { viewModel.search } returns MutableStateFlow("")
            every { viewModel.usersOnline } returns MutableStateFlow(fakeUsers)

            onNodeWithTag(ChatsScreenTags.InfoScreen.toString()).assertDoesNotExist()
            onNodeWithTag(ChatsScreenTags.ChatUsersWithSearch.toString()).assertExists()
                .assertIsDisplayed()
        }

    @Test
    fun givenFakeUsersOnline_whenUserPerformsSearch_thenUserListIsFiltered(): Unit =
        with(composeTestRule) {
            coEvery { viewModel.updateUiConfiguration(any()) } just Runs
            every { viewModel.search } returns MutableStateFlow(fakeUsers.first().username)
            every { viewModel.usersOnline } returns MutableStateFlow(fakeUsers)

            onNodeWithTag(ChatsScreenTags.InfoScreen.toString()).assertDoesNotExist()
            onNodeWithTag(ChatsScreenTags.ChatUsersWithSearch.toString()).assertExists()
                .assertIsDisplayed()
        }

    @Test
    fun givenFakeUsersOnline_WhenUserClicksOnSomeUser_thenOnStartChatIsTriggered(): Unit =
        with(composeTestRule) {
            coEvery { viewModel.updateUiConfiguration(any()) } just Runs
            every { viewModel.search } returns MutableStateFlow("ur")
            every { viewModel.usersOnline } returns MutableStateFlow(fakeUsers)

            onNodeWithText(fakeUsers.first().username).performClick()
            verify(exactly = 1) { onStartChat() }
        }
}
