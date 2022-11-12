package com.example.mumble.ui.utils

import androidx.navigation.NavController
import com.example.mumble.ui.navigation.Screen

fun NavController.navigate(origin: Screen, destination: Screen) {
    when (destination) {
        Screen.Onboarding -> {
            navigate(destination.route) {
                popUpTo(origin.route) {
                    inclusive = true
                }
            }
        }
        Screen.Introduction -> {
            navigate(destination.route) {
                popUpTo(origin.route) {
                    inclusive = true
                }
            }
        }
        is Screen.Chat -> {
            navigate(destination.route)
        }
        is Screen.Chats -> {
            navigate(destination.route) {
                popUpTo(origin.route) {
                    inclusive = true
                }
            }
        }
        else -> throw Exception("Should not navigate back to ${origin.route}")
    }
}
