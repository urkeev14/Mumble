package com.example.mumble.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

sealed class State<T>(val data: T? = null, val message: UiMessage? = null) {
    /**
     * Indicates that [data] for the screen is being processed.
     *
     * @param data data processed from a specific data source.
     */
    class Loading<T>(data: T? = null, message: UiMessage? = null) : State<T>(data, message)

    sealed class Outcome<T>(data: T? = null, message: UiMessage? = null) : State<T>(data, message) {

        /**
         * Represents valid screen state.
         *
         * @param data data processed from a specific data source.
         */
        class Success<T>(data: T, message: UiMessage? = null) : Outcome<T>(data, message)

        /**
         * Represents invalid UI state.
         *
         * @param message error message received (or resolved) from a specific data source.
         * @param backupData data loaded in case when data source throws an exception.
         */
        class Error<T>(backupData: T? = null, message: UiMessage) : Outcome<T>(backupData, message)
    }

    fun isLoading() = this is Loading

    fun isSuccess() = this is Outcome.Success

    fun isError() = this is Outcome.Error

    @Composable
    fun getMessageOrNull(): String? {
        if (message == null) return null
        return message.asString(LocalContext.current)
    }
}

sealed class UiMessage(val isError: Boolean) {
    class SimpleString(val value: String, isError: Boolean = false) : UiMessage(isError)
    class StringResource(
        @StringRes val resId: Int,
        isError: Boolean,
        vararg val args: Any
    ) : UiMessage(isError)

    fun asString(context: Context): String {
        return when (this) {
            is SimpleString -> value
            is StringResource -> context.resources.getString(resId, *args)
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is SimpleString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }
}
