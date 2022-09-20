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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mumble.R
import com.example.mumble.domain.model.ToolbarConfiguration
import com.example.mumble.ui.navigation.Screen
import com.example.mumble.ui.screens.chats.ChatsTabs
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceS
import com.example.mumble.ui.utils.navigate

@Composable
fun MyTopAppBar(
    config: ToolbarConfiguration,
    navController: NavController = rememberNavController(),
) {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf<ChatsTabs>(ChatsTabs.Conversations) }
    AnimatedVisibility(visible = config.screen !is Screen.Onboarding) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            tonalElevation = 4.dp
        ) {
            Column {
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
                        text = config.title,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    if (navController.previousBackStackEntry != null && config.screen !is Screen.Chats) {
                        IconButton(onClick = navController::navigateUp) {
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
                if (config.screen is Screen.Chats) {
                    TabRow(
                        selectedTabIndex = selectedTab.index,
                        divider = {}
                    ) {
                        ChatsTabs.children().forEach { item ->
                            Tab(
                                modifier = Modifier.wrapContentWidth(),
                                selected = selectedTab.index == item.index,
                                onClick = {
                                    if (selectedTab.index == item.index) return@Tab

                                    navController.navigate(Screen.Chats.getFromTab(item))
                                    setSelectedTab(ChatsTabs.convert(item.index))
                                }
                            ) {
                                Text(
                                    text = stringResource(id = item.tabNameStringRes),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(2.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyTopAppBar() {
    MumbleTheme {
        MyTopAppBar(
            config = ToolbarConfiguration("Lets chat!", screen = Screen.Chats.Conversations),
            navController = rememberNavController()
        )
    }
}
