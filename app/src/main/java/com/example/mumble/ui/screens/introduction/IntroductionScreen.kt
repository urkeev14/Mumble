package com.example.mumble.ui.screens.introduction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mumble.R
import com.example.mumble.ui.components.Disclaimer
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.utils.extensions.observeFlowSafely

@Composable
fun IntroductionScreen(
    viewModel: IntroductionViewModel = hiltViewModel(),
    onNicknameSubmittedSuccessfully: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val error by viewModel.stateErrorMessage.collectAsState(initial = null)

    viewModel.stateGoToChatsScreen.observeFlowSafely(action = { goToChatsScreen ->
        if (goToChatsScreen) {
            onNicknameSubmittedSuccessfully()
        }
    })

    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBar {
            Row(
                Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo"
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(id = R.string.introduce_yourself)
                )
            }
        }
    }) {

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
                .padding(it)
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
                    value = viewModel.nickname.value,
                    onValueChange = { value ->
                        viewModel.setNickname(value)
                    },
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
                    .padding(32.dp),
                onClick = {
                    viewModel.validateNickname()
                }
            ) {
                Text(text = stringResource(id = R.string.go))
            }
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

@Preview
@Composable
fun IntroductionScreenPreview() {
    MumbleTheme(darkTheme = true) {
        IntroductionScreen {}
    }
}
