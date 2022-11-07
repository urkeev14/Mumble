package com.example.mumble.injection

import android.content.Context
import android.net.nsd.NsdManager
import com.example.mumble.domain.usecase.CreateUserUseCase
import com.example.mumble.domain.usecase.DeleteUserUseCase
import com.example.mumble.domain.usecase.ReadAllUsersOnlineUseCase
import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.domain.usecase.SetUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateCurrentUserUseCase
import com.example.mumble.domain.usecase.UpdateUiConfigurationUseCase
import com.example.mumble.services.manager.IUserDiscoveryManager
import com.example.mumble.services.manager.UserAnnouncementManager
import com.example.mumble.services.manager.UserDiscoveryManager
import com.example.mumble.ui.IUiManager
import com.example.mumble.ui.UiManager
import com.example.mumble.ui.screens.chat.notification.NotificationHandler
import com.example.mumble.ui.screens.chat.notification.NotificationHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideUiManager(
        updateUiConfigurationUseCase: UpdateUiConfigurationUseCase,
        setUiMessageUseCase: SetUiMessageUseCase
    ): IUiManager {
        return UiManager(updateUiConfigurationUseCase, setUiMessageUseCase)
    }

    @Singleton
    @Provides
    fun provideNsdManager(
        @ApplicationContext context: Context
    ): NsdManager {
        return (context.getSystemService(Context.NSD_SERVICE) as NsdManager)
    }

    @Singleton
    @Provides
    fun provideNotificationHandler(
        @ApplicationContext context: Context
    ): NotificationHandler {
        return NotificationHandlerImpl(context)
    }

    @Singleton
    @Provides
    fun provideUserAnnouncementManager(
        nsdManager: NsdManager,
        readCurrentUserUseCase: ReadCurrentUserUseCase,
        setUiMessageUseCase: SetUiMessageUseCase,
        deleteUserUseCase: DeleteUserUseCase,
        updateCurrentUserUseCase: UpdateCurrentUserUseCase,
        readAllUsersOnlineUseCase: ReadAllUsersOnlineUseCase
    ): UserAnnouncementManager {
        return UserAnnouncementManager(
            nsdManager,
            readCurrentUserUseCase,
            setUiMessageUseCase,
            deleteUserUseCase,
            updateCurrentUserUseCase,
            readAllUsersOnlineUseCase
        )
    }

    @Singleton
    @Provides
    fun provideUserDiscoveryManager(
        nsdManager: NsdManager,
        createUserUseCase: CreateUserUseCase,
        deleteUserUseCase: DeleteUserUseCase,
        readCurrentUserUseCase: ReadCurrentUserUseCase,
        updateCurrentUserUseCase: UpdateCurrentUserUseCase,
        readAllUsersOnlineUseCase: ReadAllUsersOnlineUseCase
    ): IUserDiscoveryManager {
        return UserDiscoveryManager(
            nsdManager,
            createUserUseCase,
            readCurrentUserUseCase,
            deleteUserUseCase,
            updateCurrentUserUseCase,
            readAllUsersOnlineUseCase
        )
    }
}
