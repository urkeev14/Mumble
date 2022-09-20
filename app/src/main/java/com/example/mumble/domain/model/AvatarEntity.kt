package com.example.mumble.domain.model

import com.example.mumble.ui.theme.RandomColors

data class AvatarEntity(
    val monogram: String = "",
    var color: ULong = RandomColors.random().value
)
