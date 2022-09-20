package com.example.mumble.ui.screens.onboarding

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mumble.R
import com.example.mumble.domain.model.ToolbarConfiguration
import com.example.mumble.domain.model.UiConfiguration
import com.example.mumble.ui.components.Logo
import com.example.mumble.ui.navigation.Screen
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.utils.navigate
import com.example.mumble.utils.extensions.observeFlowSafely
import com.example.mumble.utils.extensions.usingContext

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
) {
    usingContext {
        viewModel.updateUiConfiguration(
            UiConfiguration(
                ToolbarConfiguration(
                    title = it.resources.getString(R.string.onboarding),
                    isVisible = false,
                    screen = Screen.Onboarding
                )
            )
        )
    }

    viewModel.isUserOnboarded.observeFlowSafely { isUserOnboarded ->
        if (isUserOnboarded) {
            navController.navigate(Screen.Introduction)
        }
    }

    AnimatedVisibility(
        visible = viewModel.isLogoVisible.value,
        enter = fadeIn(animationSpec = tween(OnboardingViewModel.ANIMATION_TIME)),
    ) {
        OnboardingContent()
    }
}

@Composable
private fun OnboardingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Text(
            text = stringResource(id = R.string.lets_have_fun),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewOnboardingScreen() {
    MumbleTheme() {
        Surface {
            OnboardingContent()
        }
    }
}
