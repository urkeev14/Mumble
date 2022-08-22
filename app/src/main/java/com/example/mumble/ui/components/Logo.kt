package com.example.mumble.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
        Text(text = "Let's have some fun", style = MaterialTheme.typography.body1)
    }
}

@Preview
@Composable
private fun previewLogo(){
    MumbleTheme {
        Logo()
    }
}