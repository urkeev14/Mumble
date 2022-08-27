package com.example.mumble.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mumble.R
import com.example.mumble.ui.theme.MumbleTheme

@Composable
fun Logo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val vector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
        Image(
            imageVector = vector,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewLogo() {
    MumbleTheme(darkTheme = true) {
        Logo()
    }
}
