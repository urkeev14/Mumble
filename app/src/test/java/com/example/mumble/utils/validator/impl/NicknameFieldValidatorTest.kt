package com.example.mumble.utils.validator.impl

import com.example.mumble.utils.State
import com.example.mumble.utils.validator.FieldValidator
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.Serializable

@RunWith(value = Parameterized::class)
class NicknameFieldValidatorTest(
    private val methodName: String,
    private val sut: String,
    private val expected: Class<State.Outcome<Unit>>
) {

    private lateinit var validator: FieldValidator

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = " \uD83E\uDDEA {0} ➡ [{1}] ➡ {2}")
        fun data(): List<Array<Serializable>> {
            val errorClass = State.Outcome.Error::class.java

            return arrayListOf(
                arrayOf("Empty string", "", errorClass),
                arrayOf("Too short string", "aa", errorClass),
                arrayOf("Only special chars", "_$$", errorClass),
                arrayOf("With numbers", "invalid14", State.Outcome.Success::class.java),
                arrayOf("Restricted special char", "invalid#14", errorClass),
                arrayOf("No small chars", "TESTTEST14", errorClass),
                arrayOf("No special chars", "Test14", errorClass),
                arrayOf("Starts with underscore", "_test14", errorClass),
                arrayOf("Ends with underscore", "test14_", errorClass),
            ).toList()
        }
    }

    @Before
    fun setUp() {
        validator = NicknameFieldValidator()
    }

    @Test
    fun test() {
        val actual = validator.validate(sut)
        assertEquals(expected, actual::class.java)
    }
}
