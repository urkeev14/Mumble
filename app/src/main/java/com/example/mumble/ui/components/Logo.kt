package com.example.mumble.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mumble.R
import com.example.mumble.ui.theme.MumbleTheme

@Composable
fun Logo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo"
        )
    }
}

@Preview
@Composable
private fun PreviewLogo() {
    MumbleTheme {
        Logo()
    }
}

@Preview
@Composable
private fun PreviewShit() {
    // todo
}
