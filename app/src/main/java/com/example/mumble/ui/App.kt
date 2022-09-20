package com.example.mumble.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mumble.ui.components.Toolbar
import com.example.mumble.ui.navigation.Route
import com.example.mumble.ui.screens.chat.ChatScreen
import com.example.mumble.ui.screens.chats.ChatsScreen
import com.example.mumble.ui.screens.introduction.IntroductionScreen
import com.example.mumble.ui.screens.onboarding.OnboardingScreen

@Composable
fun App(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.ONBOARDING
) {

    val context = LocalContext.current
    val uiConfiguration = viewModel.uiConfiguration.collectAsState()
    val uiMessage = viewModel.uiMessage.collectAsState(initial = null)
    val toolbarConfig = uiConfiguration.value.toolbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)

    LaunchedEffect(uiMessage.value) {
        viewModel.uiMessage.collect {
            snackbarHostState.showSnackbar(it.asString(context))
        }
    }

    Scaffold(scaffoldState = scaffoldState, topBar = {
        Toolbar(config = toolbarConfig, navController = navController)
    }) { padding ->
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Route.ONBOARDING) {
                OnboardingScreen {
                    navController.navigate(Route.INTRODUCTION) {
                        popUpTo(Route.INTRODUCTION)
                    }
                }
            }
            composable(Route.INTRODUCTION) {
                IntroductionScreen(padding) {
                    navController.navigate(Route.CHATS)
                }
            }
            composable(Route.CHATS) {
                ChatsScreen(padding) {
                    navController.navigate(Route.CHAT with it.username) {
                        popUpTo(Route.CHATS)
                    }
                }
            }
            composable(Route.CHAT) { backstackEntry ->
                val userId = backstackEntry.arguments?.getString("userId") ?: "-1"
                ChatScreen(userId)
            }
        }
    }
}

infix fun String.with(appended: String) = this.replaceAfter("/", appended)
