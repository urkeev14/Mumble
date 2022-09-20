package com.example.mumble.injection

import com.example.mumble.data.mapper.Mapper
import com.example.mumble.data.mapper.impl.DateMapper
import com.example.mumble.data.mapper.impl.dto.MessageDtoMapper
import com.example.mumble.data.mapper.impl.entity.MessageEntityMapper
import com.example.mumble.data.mapper.impl.entity.UserEntityMapper
import com.example.mumble.data.mapper.impl.ui.AvatarMapper
import com.example.mumble.data.mapper.impl.ui.MessageWithUserMapper
import com.example.mumble.data.mapper.impl.ui.UserMapper
import com.example.mumble.data.repository.impl.ChatRepository
import com.example.mumble.data.repository.impl.ConnectivityRepository
import com.example.mumble.data.repository.impl.UiRepository
import com.example.mumble.data.repository.source.local.ChatLocalDataSource
import com.example.mumble.data.repository.source.local.ConnectivityLocalDataSource
import com.example.mumble.domain.dto.MessageDto
import com.example.mumble.domain.dto.UserDto
import com.example.mumble.domain.model.AvatarEntity
import com.example.mumble.domain.model.MessageEntity
import com.example.mumble.domain.model.UserEntity
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.domain.repository.IConnectivityRepository
import com.example.mumble.domain.repository.IUiRepository
import com.example.mumble.domain.repository.source.IChatLocalDataSource
import com.example.mumble.domain.repository.source.IConnectivityLocalDataSource
import com.example.mumble.domain.usecase.MessageWithUser
import com.example.mumble.ui.model.Avatar
import com.example.mumble.ui.model.Message
import com.example.mumble.ui.model.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    // ========== Repositories ==========
    @Singleton
    @Provides
    fun provideChatRepository(localDataSource: IChatLocalDataSource): IChatRepository {
        return ChatRepository(localDataSource)
    }

    @Singleton
    @Provides
    fun provideConnectivityRepository(dataSource: IConnectivityLocalDataSource): IConnectivityRepository {
        return ConnectivityRepository(dataSource)
    }

    @Singleton
    @Provides
    fun provideUiRepository(): IUiRepository {
        return UiRepository()
    }

    // ========== Data sources ==========
    @Singleton
    @Provides
    fun providesChatLocalDataSource(): IChatLocalDataSource {
        return ChatLocalDataSource(
            MutableStateFlow(UserEntity()),
            MutableStateFlow(listOf()),
            MutableStateFlow(listOf()),
            MutableSharedFlow(replay = 1),
            MutableSharedFlow(replay = 1)
        )
    }

    @Singleton
    @Provides
    fun provideIConnectivityLocalDataSource(): IConnectivityLocalDataSource {
        return ConnectivityLocalDataSource()
    }

    // ========== Mappers ==========
    @Singleton
    @Provides
    fun provideAvatarMapper(): Mapper<AvatarEntity, Avatar> {
        return AvatarMapper()
    }

    @Singleton
    @Provides
    fun provideUserMapper(mapper: Mapper<AvatarEntity, Avatar>): Mapper<UserEntity, User> {
        return UserMapper(mapper)
    }

    @Singleton
    @Provides
    fun provideUserEntityMapper(): Mapper<UserEntity, UserDto> {
        return UserEntityMapper()
    }

    @Singleton
    @Provides
    fun provideDateMapper(): Mapper<Long, String> {
        return DateMapper()
    }

    @Singleton
    @Provides
    fun provideMessageEntityMapper(): Mapper<MessageEntity, MessageDto> {
        return MessageEntityMapper()
    }

    @Singleton
    @Provides
    fun provideMessageDtoMapper(): Mapper<MessageDto, MessageEntity> {
        return MessageDtoMapper()
    }

    @Singleton
    @Provides
    fun provideMessageWithUserMapper(
        userMapper: Mapper<UserEntity, User>,
        dateMapper: Mapper<Long, String>
    ): Mapper<MessageWithUser, Message> {
        return MessageWithUserMapper(userMapper, dateMapper)
    }
}
