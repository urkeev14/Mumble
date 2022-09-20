package com.example.mumble.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mumble.domain.model.Avatar
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.utils.contrastColorFrom
import com.example.mumble.utils.extensions.gradientBackground
import com.example.mumble.utils.extensions.toSp

private const val GRADIENT_ANGLE = 60f

@Composable
fun AvatarItem(
    avatar: Avatar,
    size: Dp = 50.dp,
    modifier: Modifier = Modifier
) {
    val backgroundColor = avatar.color
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .gradientBackground(
                    listOf(contrastColorFrom(backgroundColor), backgroundColor),
                    GRADIENT_ANGLE
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = avatar.monogram.uppercase(),
                Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                fontSize = size.toSp() / 2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAvatarItem() {
    MumbleTheme {
        AvatarItem(avatar = Avatar("U"))
    }
}
