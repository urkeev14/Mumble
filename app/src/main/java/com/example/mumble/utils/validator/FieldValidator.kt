package com.example.mumble.utils.validator

import com.example.mumble.utils.State
import com.example.mumble.utils.UiMessage

abstract class FieldValidator {

    abstract val regex: Regex

    abstract val errorEmptyString: Int
    abstract val errorInvalidString: Int

    fun validate(value: String): State.Outcome<Unit> {
        return when {
            value.isEmpty() -> State.Outcome.Error(
                message = UiMessage.StringResource(
                    resId = errorEmptyString,
                    isError = true
                )
            )
            regex.matches(value) -> State.Outcome.Success(Unit)
            else -> State.Outcome.Error(
                message = UiMessage.StringResource(
                    resId = errorInvalidString,
                    isError = true
                )
            )
        }
    }
}
