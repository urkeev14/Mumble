package com.example.mumble.utils.validator.impl

import com.example.mumble.R
import com.example.mumble.utils.validator.FieldValidator

class NicknameFieldValidator : FieldValidator() {
    override val regex: Regex
        get() = Regex("^(?=.{4,15}\$)(?![_.])(?!.*[_.]{2})[a-z0-9._]+(?<![_.])\$")

    override val errorEmptyString: Int
        get() = R.string.nickname_empty

    override val errorInvalidString: Int
        get() = R.string.nickname_invalid
}
