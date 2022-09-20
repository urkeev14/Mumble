package com.example.mumble.ui.screens.chats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mumble.R
import com.example.mumble.ui.theme.MumbleTheme

@Composable
fun NobodyOnlineScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {

        Icon(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.ic_alone),
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.size(width = 0.dp, height = 16.dp))
        Text(
            text = stringResource(id = R.string.nobody_online),
            style = MaterialTheme.typography.h5,
        )
        Spacer(modifier = Modifier.size(width = 0.dp, height = 24.dp))
        Text(
            text = stringResource(id = R.string.nobody_online_description),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNobodyOnlineScreen() {
    MumbleTheme {
        NobodyOnlineScreen()
    }
}
