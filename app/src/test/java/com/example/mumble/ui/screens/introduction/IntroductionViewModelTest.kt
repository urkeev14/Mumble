package com.example.mumble.ui.screens.introduction

import com.example.mumble.domain.usecase.UpdateCurrentUsersNicknameUseCase
import com.example.mumble.ui.IUiManager
import com.example.mumble.utils.State
import com.example.mumble.utils.UiMessage
import com.example.mumble.utils.validator.impl.NicknameFieldValidator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class IntroductionViewModelTest {

    private lateinit var uiManager: IUiManager
    private lateinit var validator: NicknameFieldValidator
    private lateinit var updateCurrentUsersNicknameUseCase: UpdateCurrentUsersNicknameUseCase
    private lateinit var sut: IntroductionViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        uiManager = mockk()
        validator = spyk()
        updateCurrentUsersNicknameUseCase = mockk()
        sut = IntroductionViewModel(uiManager, validator, updateCurrentUsersNicknameUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `validateNickname calls handleValidNickname once when nickname is valid`() {
        val errorState = mockk<State.Outcome.Error<Unit>>()
        every { errorState.message } returns UiMessage.SimpleString("")
        every { validator.validate(any()) } returns errorState

        with(sut) {
            validateNickname()
            verify(exactly = 1) {
                sut.handleInvalidNickname(errorState)
            }
        }
    }
    /*TODO: Fix when issue get resolved [https://github.com/mockk/mockk/issues/637]
    @Test
    fun `validateNickname calls handleInvalidNickname once when nickname is invalid`() = runTest{
        val successState = mockk<State.Outcome.Success<Unit>>()
        every { successState.message } returns UiMessage.SimpleString("")
        every { validator.validate(any()) } returns successState

        with(sut) {
            validateNickname()
            verify(exactly = 1) {
                sut.handleValidNickname()
            }
        }
    }
    */

    @Test
    fun `handleValidNickname sets nicknameState to true and emits event to go to Chats screen`(): Unit =
        runTest {
            coEvery { updateCurrentUsersNicknameUseCase(any()) } returns Unit
            with(sut) {
                handleValidNickname()
                val isGoToChatsScreen = stateGoToChatsScreen.first()
                val isErrorMessageEmpty = stateErrorMessage.value == null
                assert(isGoToChatsScreen)
                assert(isErrorMessageEmpty)
            }
        }

    @Test
    fun `handleInvalidNickname sets error message`() {
        with(sut) {
            val error = mockk<State.Outcome.Error<Unit>>()
            val expectedMessage = mockk<UiMessage>()
            every { error.message } returns expectedMessage

            handleInvalidNickname(error)

            val actualMessage = stateErrorMessage.value
            assertEquals(expectedMessage, actualMessage)
        }
    }

    @Test
    fun `setNickname sets nickname`() = runBlocking {
        val expectedNickname = "nickname"
        with(sut) {
            setNickname(expectedNickname)

            val actualNickname = nickname.value
            assertEquals(expectedNickname, actualNickname)
        }
    }
}
