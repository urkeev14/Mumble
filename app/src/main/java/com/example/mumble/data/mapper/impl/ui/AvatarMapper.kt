package com.example.mumble.data.mapper.impl.ui

import androidx.compose.ui.graphics.Color
import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.model.AvatarEntity
import com.example.mumble.ui.model.Avatar

class AvatarMapper : Mapper<AvatarEntity, Avatar> {
    override fun map(input: AvatarEntity): Avatar {
        return Avatar(monogram = input.monogram, color = Color(input.color))
    }
}
