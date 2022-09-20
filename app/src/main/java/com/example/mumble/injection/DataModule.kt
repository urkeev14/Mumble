package com.example.mumble.injection

import com.example.mumble.data.repository.impl.ChatRepository
import com.example.mumble.data.repository.impl.ConnectivityRepository
import com.example.mumble.data.repository.impl.UiRepository
import com.example.mumble.data.repository.source.local.ChatLocalDataSource
import com.example.mumble.data.repository.source.local.ConnectivityLocalDataSource
import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.domain.repository.IConnectivityRepository
import com.example.mumble.domain.repository.IUiRepository
import com.example.mumble.domain.repository.source.IChatLocalDataSource
import com.example.mumble.domain.repository.source.IConnectivityLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideChatRepository(localDataSource: IChatLocalDataSource): IChatRepository {
        return ChatRepository(localDataSource)
    }

    @Singleton
    @Provides
    fun providesChatLocalDataSource(): IChatLocalDataSource {
        return ChatLocalDataSource()
    }

    @Singleton
    @Provides
    fun provideConnectivityRepository(dataSource: IConnectivityLocalDataSource): IConnectivityRepository {
        return ConnectivityRepository(dataSource)
    }

    @Singleton
    @Provides
    fun provideIConnectivityLocalDataSource(): IConnectivityLocalDataSource {
        return ConnectivityLocalDataSource()
    }

    @Singleton
    @Provides
    fun provideErrorRepository(): IUiRepository {
        return UiRepository()
    }
}
