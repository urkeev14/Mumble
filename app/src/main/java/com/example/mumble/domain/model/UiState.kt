package com.example.mumble.domain.model

import androidx.compose.ui.graphics.Color
import com.example.mumble.ui.navigation.Screen
import java.util.UUID

/**
 * TODO: Create sub-configurations for toolbar and other scaffold sub-components
 *
 * @property appBarTitle
 * @property isBackButtonVisible
 * @property isToolbarVisible
 */
data class UiState(
    val toolbar: ToolbarState = ToolbarState(screen = Screen.Introduction)
) {
    override fun equals(other: Any?): Boolean {
        return other is UiState && other.toolbar.id == toolbar.id && other.toolbar.screen == toolbar.screen
    }

    override fun hashCode(): Int {
        return toolbar.id.hashCode()
    }
}

data class ToolbarState(
    val title: String = "",
    val isVisible: Boolean = true,
    val color: Color? = null,
    val isBackButtonVisible: Boolean = false,
    val screen: Screen
) {
    val id: UUID = UUID.randomUUID()

    override fun equals(other: Any?): Boolean {
        return other is ToolbarState && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
