package com.example.mumble.ui.screens.introduction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mumble.R
import com.example.mumble.domain.model.ToolbarConfiguration
import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.ui.components.Disclaimer
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.utils.UiMessage
import com.example.mumble.utils.contrastColorFrom
import com.example.mumble.utils.extensions.observeFlowSafely
import com.example.mumble.utils.extensions.usingContext

@Composable
fun IntroductionScreen(
    padding: PaddingValues,
    viewModel: IntroductionViewModel = hiltViewModel(),
    onNicknameSubmittedSuccessfully: () -> Unit,
) {
    // TODO: Implement sending a flag to repository flowable that triggers STOP action of ChatAnnouncementService

    val error by viewModel.stateErrorMessage.collectAsState(initial = null)
    val nickname by viewModel.nickname

    viewModel.stateGoToChatsScreen.observeFlowSafely(action = { goToChatsScreen ->
        if (goToChatsScreen) {
            onNicknameSubmittedSuccessfully()
        }
    })

    usingContext {
        viewModel.updateUiConfiguration(
            UiConfiguration(ToolbarConfiguration(title = it.resources.getString(R.string.introduction)))
        )
    }

    IntroductionScreenContent(
        padding,
        nickname,
        viewModel::setNickname,
        viewModel::validateNickname,
        error
    )
}

@Composable
private fun IntroductionScreenContent(
    padding: PaddingValues = PaddingValues(),
    nickname: String = "",
    onNicknameChange: (String) -> Unit = {},
    onValidateNickname: () -> Unit = {},
    error: UiMessage? = null
) {
    Disclaimer(
        modifier = Modifier.padding(16.dp),
        dividerWidth = 4.dp,
        text = stringResource(id = R.string.introduction_disclaimer)
    )

    ConstraintLayout(
        introductionScreenContentConstraintSet(),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(padding)
    ) {
        Column(
            modifier = Modifier
                .layoutId("textField")
                .wrapContentWidth()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                value = nickname,
                onValueChange = onNicknameChange::invoke,
                isError = error?.asString().isNullOrEmpty().not(),
                placeholder = { Text(text = stringResource(id = R.string.nickname)) }
            )
            error?.asString()?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .layoutId("errorText")
                        .padding(start = 48.dp, end = 48.dp)
                )
            }
        }

        Button(
            modifier = Modifier
                .layoutId("button")
                .fillMaxWidth()
                .height(40.dp)
                .padding(32.dp),
            onClick = onValidateNickname::invoke
        ) {
            Text(text = stringResource(id = R.string.go))
        }
    }
}

private fun introductionScreenContentConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val textField = createRefFor("textField")
        val button = createRefFor("button")

        constrain(textField) {
            centerHorizontallyTo(parent)
            centerVerticallyTo(parent)
        }
        constrain(button) {
            bottom.linkTo(parent.bottom)
            centerHorizontallyTo(parent)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun IntroductionScreenPreview() {
    MumbleTheme(darkTheme = true) {
        IntroductionScreen(PaddingValues(8.dp)) {}
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewPrimaryButton() {
    val background = MaterialTheme.colors.onBackground
    MumbleTheme {
        val gradient =
            Brush.horizontalGradient(listOf(background, contrastColorFrom(background)))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            contentPadding = PaddingValues(),
            onClick = {},
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .background(gradient)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(id = R.string.go))
            }
        }
    }
}
