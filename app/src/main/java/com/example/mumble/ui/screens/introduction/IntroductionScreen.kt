package com.example.mumble.ui.screens.introduction

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mumble.R
import com.example.mumble.domain.model.ToolbarConfiguration
import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.ui.components.Disclaimer
import com.example.mumble.ui.components.ValidatedInputField
import com.example.mumble.ui.navigation.Screen
import com.example.mumble.ui.tags.IntroductionScreenTags
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceM
import com.example.mumble.ui.theme.spaceS
import com.example.mumble.ui.utils.navigate
import com.example.mumble.utils.UiMessage
import com.example.mumble.utils.extensions.observeFlowSafely
import com.example.mumble.utils.extensions.usingContext

val tagDisclaimer = IntroductionScreenTags.Disclaimer.toString()
val tagNickname = IntroductionScreenTags.NicknameTextField.toString()
val tagError = IntroductionScreenTags.ErrorText.toString()
val tagGoButton = IntroductionScreenTags.GoButton.toString()

@Composable
fun IntroductionScreen(
    navController: NavController = rememberNavController(),
    viewModel: IntroductionViewModel = hiltViewModel(),
) {
    // TODO: Implement sending a flag to repository flowable that triggers STOP action of ChatAnnouncementService

    val error by viewModel.stateErrorMessage.collectAsState(initial = null)
    val nickname by viewModel.nickname

    usingContext {
        viewModel.updateUiConfiguration(
            UiConfiguration(
                ToolbarConfiguration(
                    title = it.resources.getString(R.string.introduction),
                    screen = Screen.Introduction
                )
            )
        )
    }

    viewModel.stateGoToChatsScreen.observeFlowSafely(action = { goToChatsScreen ->
        if (goToChatsScreen) {
            navController.navigate(Screen.Chats.Conversations)
        }
    })

    IntroductionScreenContent(
        nickname,
        viewModel::setNickname,
        viewModel::validateNickname,
        error
    )
}

@Composable
fun IntroductionScreenContent(
    nickname: String = "",
    onNicknameChange: (String) -> Unit = {},
    onValidateNickname: () -> Unit = {},
    error: UiMessage? = null
) {

    ConstraintLayout(
        introductionScreenContentConstraintSet(),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(spaceS)
    ) {
        Disclaimer(
            modifier = Modifier
                .padding(spaceM)
                .layoutId(tagDisclaimer)
                .testTag(tagDisclaimer),
            dividerWidth = 4.dp,
            text = stringResource(id = R.string.introduction_disclaimer)
        )

        ValidatedInputField(
            modifier = Modifier
                .layoutId(tagNickname)
                .wrapContentWidth(),
            value = nickname,
            onValueChange = onNicknameChange,
            error = error,
            label = stringResource(id = R.string.nickname)
        )

        Button(
            modifier = Modifier
                .layoutId(tagGoButton)
                .testTag(tagGoButton)
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = spaceM),
            onClick = onValidateNickname::invoke
        ) {
            Text(
                text = stringResource(id = R.string.confirm),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

private fun introductionScreenContentConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val disclaimer = createRefFor(tagDisclaimer)
        val textField = createRefFor(tagNickname)
        val button = createRefFor(tagGoButton)

        constrain(disclaimer) {
            centerHorizontallyTo(parent)
            top.linkTo(parent.top)
        }
        constrain(textField) {
            centerHorizontallyTo(parent)
            centerVerticallyTo(parent)
        }
        constrain(button) {
            centerHorizontallyTo(parent)
            bottom.linkTo(parent.bottom, margin = spaceM)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun IntroductionScreenPreview() {
    MumbleTheme(darkTheme = false) {
        IntroductionScreenContent()
    }
}
