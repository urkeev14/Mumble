package com.example.mumble.domain.model

import androidx.compose.ui.graphics.Color

/**
 * TODO: Create sub-configurations for toolbar and other scaffold sub-components
 *
 * @property appBarTitle
 * @property isBackButtonVisible
 * @property isToolbarVisible
 */
data class UiConfiguration(
    val toolbar: ToolbarConfiguration = ToolbarConfiguration()
)

data class ToolbarConfiguration(
    val title: String = "",
    val isVisible: Boolean = true,
    val color: Color? = null,
    val isBackButtonVisible: Boolean = false,
)
