package com.example.mumble.utils

import androidx.compose.ui.graphics.Color

private const val HEX_WHITE = 0x00ffffff

fun contrastColorFrom(color: Color): Color {
    return Color(color.value.toInt() shr HEX_WHITE)
}
