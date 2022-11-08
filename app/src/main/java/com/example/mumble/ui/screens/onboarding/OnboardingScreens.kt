package com.example.mumble.ui.screens.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mumble.ui.navigation.Screen
import com.example.mumble.ui.screens.info.InfoScreen
import com.example.mumble.ui.screens.info.InfoScreenVariants
import com.example.mumble.ui.theme.spaceXXL
import com.example.mumble.ui.utils.navigate
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreens(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {

    val screens = InfoScreenVariants.onboardingScreens

    val pagerState = rememberPagerState()

    Box(modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(
            count = screens.count(),
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            InfoScreen(variant = screens[position]) {
                navController.navigate(Screen.Introduction)
            }
        }
        HorizontalPagerIndicator(
            modifier = Modifier.padding(bottom = spaceXXL),
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.primaryContainer
        )
    }
}

@Preview
@Composable
fun PreviewWelcomeScreen() {
}
