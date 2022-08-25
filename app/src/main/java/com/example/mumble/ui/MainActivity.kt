package com.example.mumble.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mumble.ui.components.Logo
import com.example.mumble.ui.navigation.Route
import com.example.mumble.ui.screens.chat.ChatScreen
import com.example.mumble.ui.screens.chats.ChatsScreen
import com.example.mumble.ui.screens.introduction.IntroductionScreen
import com.example.mumble.ui.screens.onboarding.OnboardingScreen
import com.example.mumble.ui.theme.MumbleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MumbleTheme {
                MumbleNavHost()
            }
        }
    }
}

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
                navController.navigate(Route.INTRODUCTION)
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

@Preview
@Composable
private fun PreviewMainActivity() {
    MumbleTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            Text(text = "Let's have some fun", style = MaterialTheme.typography.body1)
        }
    }
}
