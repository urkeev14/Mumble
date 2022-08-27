package com.example.mumble.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mumble.ui.navigation.Route
import com.example.mumble.ui.screens.chat.ChatScreen
import com.example.mumble.ui.screens.chats.ChatsScreen
import com.example.mumble.ui.screens.introduction.IntroductionScreen
import com.example.mumble.ui.screens.onboarding.OnboardingScreen

@Composable
fun MumbleNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.ONBOARDING
) {

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
            IntroductionScreen {
                navController.navigate(Route.CHATS)
            }
        }
        composable(Route.CHATS) {
            ChatsScreen {
                navController.navigate(Route.CHAT)
            }
        }
        composable(Route.CHAT) {
            ChatScreen {
                navController.navigate(Route.CHATS)
            }
        }
    }
}
