package com.example.mumble.ui.screens.introduction

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.mumble.R
import com.example.mumble.ui.MainActivity
import com.example.mumble.ui.tags.IntroductionScreenTags
import com.example.mumble.ui.theme.MumbleTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class IntroductionScreenTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    private val tagGoButton = IntroductionScreenTags.GoButton.toString()
    private val tagErrorText = IntroductionScreenTags.ErrorText.toString()

    lateinit var viewModel: IntroductionViewModel
    lateinit var onNicknameSubmittedSuccessfully: () -> Unit

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = composeTestRule.activity.viewModels<IntroductionViewModel>().value

        onNicknameSubmittedSuccessfully = mockk()
        every { onNicknameSubmittedSuccessfully() } just Runs

        composeTestRule.activity.setContent {
            MumbleTheme {
                IntroductionScreen(
                    PaddingValues(),
                    viewModel
                ) { onNicknameSubmittedSuccessfully.invoke() }
            }
        }
    }

    @Test
    fun givenValidNickname_whenClickedOnCallButton_thenOnNicknameSubmittedSuccessfullyIsCalled() =
        with(composeTestRule) {
            viewModel.setNickname("urkeev1414")
            onNodeWithTag(tagGoButton).performClick()
            onNodeWithTag(tagErrorText).assertDoesNotExist()
            verify(exactly = 1) { onNicknameSubmittedSuccessfully() }
        }

    @Test
    fun givenInvalidNickname_whenClickedOnCallButton_thenOnNicknameSubmittedSuccessfullyNotCalled_andErrorIsShown() =
        with(composeTestRule) {
            val errorText = activity.getString(R.string.nickname_invalid)

            viewModel.setNickname("ur")

            onNodeWithTag(tagGoButton).performClick()
            onNodeWithTag(tagErrorText).assertExists().assertIsDisplayed()
                .assertTextEquals(errorText)
            onNodeWithTag(tagErrorText).assertTextEquals(errorText)
            verify(exactly = 0) { onNicknameSubmittedSuccessfully() }
        }

    @Test
    fun givenNoNickname_whenClickedOnCallButton_thenOnNicknameSubmittedSuccessfullyNotCalled_andErrorIsShown() =
        with(composeTestRule) {
            val errorText = activity.getString(R.string.nickname_empty)

            viewModel.setNickname("")

            onNodeWithTag(tagGoButton).performClick()
            onNodeWithTag(tagErrorText).assertExists().assertIsDisplayed().assertTextEquals(errorText)
            onNodeWithTag(tagErrorText).assertTextEquals(errorText)
            verify(exactly = 0) { onNicknameSubmittedSuccessfully() }
        }
}
