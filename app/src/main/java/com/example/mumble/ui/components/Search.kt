package com.example.mumble.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mumble.R
import com.example.mumble.ui.theme.MumbleTheme

@Composable
fun Search(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange::invoke,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(60.dp),
            label = {
                Text(text = stringResource(id = R.string.search))
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search, contentDescription = null
                )
            },
            trailingIcon = {
                IconButton({ onValueChange("") }) {
                    Icon(Icons.Sharp.Clear, contentDescription = null)
                }
            }
        )
        Spacer(modifier = Modifier.size(height = 8.dp, width = 0.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSearch() {
    MumbleTheme {
        Search(value = "", modifier = Modifier.padding(8.dp)) {
        }
    }
}
