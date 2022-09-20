package com.example.mumble.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.mumble.ui.screens.introduction.tagError
import com.example.mumble.ui.screens.introduction.tagNickname
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceXL
import com.example.mumble.ui.theme.spaceXXL
import com.example.mumble.utils.UiMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidatedInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: UiMessage? = null
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.testTag(tagNickname)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spaceXL, end = spaceXL),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
            value = value,
            onValueChange = onValueChange::invoke,
            isError = error?.asString().isNullOrEmpty().not(),
            placeholder = { Text(text = label) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
        error?.asString()?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .layoutId(tagError)
                    .testTag(tagError)
                    .padding(start = spaceXXL, end = spaceXXL)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewValidatedInputField() {
    MumbleTheme {
        Surface {
            ValidatedInputField(
                value = "This is some search",
                onValueChange = {},
                label = "",
                error = UiMessage.SimpleString("This is some error")
            )
        }
    }
}
