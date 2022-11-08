package com.example.mumble.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.mumble.ui.components.MyTopAppBar
import com.example.mumble.ui.navigation.Screen
import com.example.mumble.ui.screens.chat.ChatScreen
import com.example.mumble.ui.screens.chats.available.AvailableChatsScreen
import com.example.mumble.ui.screens.chats.conversations.ConversationsScreen
import com.example.mumble.ui.screens.introduction.IntroductionScreen
import com.example.mumble.ui.screens.splash.SplashScreen
import com.example.mumble.ui.screens.onboarding.OnboardingScreens
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {

    val uiConfiguration by viewModel.uiConfiguration.collectAsState()

    // TODO Move navController to composable functions
    // TODO Extract composable instantiation in nav host. Create a for loop of screens,
    //  and map Screen sealed class to concrete screen

    Scaffold(topBar = {
        MyTopAppBar(config = uiConfiguration.toolbar, navController = navController)
    }) { padding ->
        NavHost(
            modifier = modifier.padding(padding),
            navController = navController,
            startDestination = uiConfiguration.toolbar.screen.route
        ) {
            composable(route = Screen.Splash.route) {
                SplashScreen(navController = navController)
            }
            composable(route = Screen.Onboarding.route) {
                OnboardingScreens(navController = navController)
            }
            composable(route = Screen.Introduction.route) {
                IntroductionScreen(navController = navController)
            }
            composable(route = Screen.Chats.AvailableUsers.route) {
                AvailableChatsScreen(navController = navController)
            }
            composable(route = Screen.Chats.Conversations.route) {
                ConversationsScreen(navController = navController)
            }
//            composable(route = Screen.Chats.route) {
//                ConversationOrAvailableChatsScreen(padding) {
//                    // TODO When navigating to this screen, make sure user can't go back to change username.
//                    navController.navigate(Screen.Chat(it.toString()).route) {
//                        popUpTo(Screen.Chats.route)
//                    }
//                }
//            }
            composable(
                route = Screen.Chat().route,
                deepLinks = listOf(navDeepLink { uriPattern = "myapp://mumble.com/chat={userId}" })
            ) { backstackEntry ->
                val userId = backstackEntry.arguments?.getString("userId") ?: "-1"
                ChatScreen(UUID.fromString(userId))
            }
        }
    }
}
