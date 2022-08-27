package com.example.mumble.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mumble.R
import com.example.mumble.ui.theme.MumbleTheme

@Composable
fun Disclaimer(
    modifier: Modifier,
    color: Color = MaterialTheme.colors.onBackground,
    dividerWidth: Dp,
    text: String
) {
    val currentLocalDensity = LocalDensity.current
    var textSize by remember { mutableStateOf(Dp.Unspecified) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Divider(
            color = color,
            modifier = Modifier
                .width(dividerWidth)
                .height(textSize + 8.dp)
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .onGloballyPositioned { ts ->
                    textSize = with(currentLocalDensity) {
                        ts.size.height.toDp()
                    }
                },
            text = text
        )
    }
}

@Preview
@Composable
fun PreviewDisclaimer() {
    MumbleTheme {
        Disclaimer(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colors.onBackground,
            dividerWidth = 4.dp,
            text = stringResource(id = R.string.introduction_disclaimer)
        )
    }
}
