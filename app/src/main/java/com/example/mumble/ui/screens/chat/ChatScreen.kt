package com.example.mumble.ui.screens.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.mumble.R

@Composable
fun ChatScreen(
    onGoBackButtonClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.chat), style = MaterialTheme.typography.h3)
        Button(onClick = onGoBackButtonClick) {
            Text(text = stringResource(id = R.string.go_back))
        }
    }
}
