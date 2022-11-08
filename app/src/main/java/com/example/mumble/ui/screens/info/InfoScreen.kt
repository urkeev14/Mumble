package com.example.mumble.ui.screens.info

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mumble.ui.tags.ChatsScreenTags
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceL
import com.example.mumble.ui.theme.spaceM
import com.example.mumble.ui.theme.spaceXL

@Composable
fun InfoScreen(
    modifier: Modifier = Modifier, variant: InfoScreenVariants, onDone: (() -> Unit)? = null
) {

    Column(
        modifier = modifier
            .testTag(ChatsScreenTags.InfoScreen.toString())
            .padding(start = spaceXL, end = spaceXL)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {

        Icon(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = variant.iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(width = 0.dp, height = spaceM))
        Text(
            text = stringResource(id = variant.titleResId),
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.size(width = 0.dp, height = spaceL))
        Text(
            text = stringResource(id = variant.descriptionResId),
            textAlign = TextAlign.Center,
        )
        variant.actionButton?.let {
            Spacer(modifier = Modifier.size(spaceXL))
            Button(onClick = { onDone?.invoke() }) {
                Text(text = stringResource(id = it))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewInfoScreen() {
    MumbleTheme {
        Surface {
            InfoScreen(variant = InfoScreenVariants.onboardingScreens.last())
        }
    }
}
