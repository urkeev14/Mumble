package com.example.mumble.ui.utils

import androidx.navigation.NavController
import com.example.mumble.ui.navigation.Screen

fun NavController.navigate(screen: Screen) {
    when (screen) {
        Screen.Onboarding -> {
            navigate(screen.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
        is Screen.Chat -> {
            navigate(screen.route)
        }
        is Screen.Chats -> {
            navigate(screen.route) {
                previousBackStackEntry?.id?.let {
                    popUpTo(it) {
                        inclusive = true
                    }
                }
            }
        }
        Screen.Introduction -> {
            navigate(screen.route) {
                previousBackStackEntry?.id?.let {
                    popUpTo(it) {
                        inclusive = true
                    }
                }
            }
        }
        else -> throw Exception("Should not navigate back to ${screen.route}")
    }
}
