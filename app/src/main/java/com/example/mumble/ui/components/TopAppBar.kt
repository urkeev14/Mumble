package com.example.mumble.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mumble.R
import com.example.mumble.domain.model.ToolbarState
import com.example.mumble.ui.AppViewModel
import com.example.mumble.ui.navigation.Screen
import com.example.mumble.ui.screens.chats.ChatsTabs
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceS
import com.example.mumble.ui.utils.navigate

@Composable
fun TopAppBar(
    viewModel: AppViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
) {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf<ChatsTabs>(ChatsTabs.Conversations) }
    val uiConfiguration by viewModel.uiState.collectAsState()
    val toolbar = uiConfiguration.toolbar

    AnimatedVisibility(visible = toolbar.screen !is Screen.Splash) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            tonalElevation = 4.dp
        ) {
            Column {
                ToolbarContent(
                    toolbar = toolbar,
                    hasScreensInBackStack = navController.previousBackStackEntry != null,
                    onNavigateUp = navController::navigateUp
                )
                if (toolbar.screen is Screen.Chats) {
                    TabRowContent(
                        selectedTab = selectedTab,
                        setSelectedTab = setSelectedTab
                    ) {
                        navController.navigate(
                            Screen.Chats.getScreen(selectedTab.index),
                            Screen.Chats.getScreen(it.index)
                        )
                    }
                    Spacer(modifier = Modifier.size(2.dp))
                }
            }
        }
    }
}

@Composable
private fun TabRowContent(
    selectedTab: ChatsTabs,
    setSelectedTab: (ChatsTabs) -> Unit,
    onNavigateToOtherTab: (ChatsTabs) -> Unit,
) {
    TabRow(selectedTabIndex = selectedTab.index, divider = {}) {
        ChatsTabs.children().forEach { item ->
            Tab(
                modifier = Modifier.wrapContentWidth(),
                selected = selectedTab.index == item.index,
                onClick = {
                    if (selectedTab.index == item.index) return@Tab
                    setSelectedTab(item)
                    onNavigateToOtherTab(item)
                }
            ) {
                Text(
                    text = stringResource(id = item.tabNameStringRes),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun ToolbarContent(
    toolbar: ToolbarState,
    hasScreensInBackStack: Boolean,
    onNavigateUp: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(spaceS)
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier
                .padding(start = spaceS)
                .fillMaxWidth(),
            text = toolbar.title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        if (hasScreensInBackStack) {
            IconButton(onClick = onNavigateUp) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        } else {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logo",
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyTopAppBar() {
    MumbleTheme {
        TopAppBar(
            navController = rememberNavController()
        )
    }
}
