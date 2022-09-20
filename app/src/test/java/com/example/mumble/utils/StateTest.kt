package com.example.mumble.utils

import org.junit.Assert.assertTrue
import org.junit.Test

internal class StateTest {

    @Test
    fun `isLoading returns true when state is Loading`() {
        val state = State.Loading(Unit)

        assertTrue(state.isLoading())
    }

    @Test
    fun `isSuccess returns true when state is Success`() {
        val state = State.Outcome.Success(Unit)

        assertTrue(state.isSuccess())
    }

    @Test
    fun `isError returns true when state is Error`() {
        val state = State.Outcome.Error<Unit>(message = UiMessage.SimpleString("some error"))

        assertTrue(state.isError())
    }
}
