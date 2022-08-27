package com.example.mumble.injection

import com.example.mumble.data.repository.IChatRepository
import com.example.mumble.data.repository.IConnectivityRepository
import com.example.mumble.data.repository.IUiRepository
import com.example.mumble.data.repository.impl.ChatRepository
import com.example.mumble.data.repository.impl.ConnectivityRepository
import com.example.mumble.data.repository.impl.UiRepository
import com.example.mumble.data.repository.source.local.IChatLocalDataSource
import com.example.mumble.data.repository.source.local.IConnectivityLocalDataSource
import com.example.mumble.data.repository.source.local.impl.ChatLocalDataSource
import com.example.mumble.data.repository.source.local.impl.ConnectivityLocalDataSource
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
