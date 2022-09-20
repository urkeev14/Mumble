package com.example.mumble.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mumble.data.fake.fakeUsers
import com.example.mumble.ui.model.Avatar
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.utils.extensions.gradientBackground
import com.example.mumble.utils.extensions.toSp

private const val GRADIENT_ANGLE = 60f

@Composable
fun AvatarItem(
    avatar: Avatar,
    size: Dp = 50.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .gradientBackground(
                listOf(avatar.color.copy(alpha = 0.4f), avatar.color),
                GRADIENT_ANGLE
            )
            .padding(top = 1.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = avatar.monogram.uppercase(),
            fontSize = size.toSp() / 2
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAvatarItem() {
    MumbleTheme {
        Surface {
            AvatarItem(avatar = fakeUsers.first().avatar)
        }
    }
}
