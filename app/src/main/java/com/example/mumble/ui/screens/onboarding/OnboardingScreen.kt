package com.example.mumble.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mumble.R
import com.example.mumble.ui.components.Logo
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = viewModel(),
    onUserOnboarded: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(null) {
        coroutineScope.launch {
            viewModel.isUserOnboarded.collect { isUserOnboarded ->
                if (isUserOnboarded) {
                    onUserOnboarded()
                }
            }
        }
    }

    AnimatedVisibility(
        visible = viewModel.isLogoVisible.value,
        enter = fadeIn(animationSpec = tween(OnboardingViewModel.ANIMATION_TIME)),
        exit = fadeOut(animationSpec = tween(OnboardingViewModel.ANIMATION_TIME))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            Text(
                text = stringResource(id = R.string.lets_have_fun),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
