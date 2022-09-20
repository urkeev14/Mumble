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
data class UiConfiguration(
    val toolbar: ToolbarConfiguration = ToolbarConfiguration(screen = Screen.Introduction)
)

data class ToolbarConfiguration(
    val title: String = "",
    val isVisible: Boolean = true,
    val color: Color? = null,
    val isBackButtonVisible: Boolean = false,
    val screen: Screen
) {
    val id: UUID = UUID.randomUUID()

    override fun equals(other: Any?): Boolean {
        return other is ToolbarConfiguration && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
