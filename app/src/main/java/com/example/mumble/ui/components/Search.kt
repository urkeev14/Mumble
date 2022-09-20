package com.example.mumble.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mumble.R
import com.example.mumble.ui.theme.MumbleTheme
import com.example.mumble.ui.theme.spaceS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange::invoke,
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        shape = RoundedCornerShape(60.dp),
        label = {
            Text(text = stringResource(id = R.string.search))
        },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            IconButton({ onValueChange("") }) {
                Icon(Icons.Sharp.Clear, contentDescription = null)
            }
        }
    )
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewSearch() {
    MumbleTheme(darkTheme = true) {
        Search(value = "asdf", modifier = Modifier.padding(spaceS)) {
        }
    }
}
