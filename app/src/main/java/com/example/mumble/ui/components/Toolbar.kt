package com.example.mumble.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mumble.R
import com.example.mumble.domain.model.ToolbarConfiguration
import com.example.mumble.ui.theme.MumbleTheme

@Composable
fun Toolbar(
    config: ToolbarConfiguration,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    AnimatedVisibility(visible = config.isVisible) {
        TopAppBar(
            modifier = modifier,
            backgroundColor = config.color ?: MaterialTheme.colors.primary,
            contentColor = contentColorFor(
                backgroundColor = config.color ?: MaterialTheme.colors.primary
            )
        ) {
            Row(
                Modifier.padding(8.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                if (config.isBackButtonVisible) {
                    IconButton(onClick = navController::popBackStack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo"
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp).fillMaxWidth(),
                    text = config.title
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewToolbar() {
    MumbleTheme {
        Toolbar(config = ToolbarConfiguration("Title"), navController = rememberNavController())
    }
}
