package com.example.mumble.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mumble.R
import com.example.mumble.ui.theme.MumbleTheme

@Composable
fun Logo(size: Dp = 240.dp) {
    Column(
        modifier = Modifier.size(size),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val vector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = vector,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLogo() {
    MumbleTheme {
        Logo()
    }
}
