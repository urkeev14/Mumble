package com.example.mumble.data.mapper.impl.entity

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.domain.dto.UserDto
import com.example.mumble.domain.model.UserEntity

class UserEntityMapper : Mapper<UserEntity, UserDto> {
    override fun map(input: UserEntity): UserDto {
        return UserDto(
            id = input.id,
            host = input.host,
            port = input.port
        )
    }
}
