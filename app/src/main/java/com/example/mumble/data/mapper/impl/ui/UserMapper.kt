package com.example.mumble.data.mapper.impl.ui

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.model.AvatarEntity
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.ui.model.Avatar
import com.example.mumble.ui.model.User
import javax.inject.Inject

class UserMapper @Inject constructor(
    private val avatarMapper: Mapper<AvatarEntity, Avatar>
) : Mapper<UserEntity, User> {
    override fun map(input: UserEntity): User {
        return User(
            id = input.id,
            username = input.username,
            isCurrentUser = input.isCurrentUser,
            isOnline = input.isOnline,
            avatar = avatarMapper.map(input.avatar)
        )
    }
}
