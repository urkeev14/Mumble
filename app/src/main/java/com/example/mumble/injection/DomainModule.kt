package com.example.mumble.injection

import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.domain.repository.IConnectivityRepository
import com.example.mumble.domain.repository.IUiRepository
import com.example.mumble.domain.usecase.ReadAllUsersOnlineUseCase
import com.example.mumble.domain.usecase.ReadCurrentUserUseCase
import com.example.mumble.domain.usecase.ReadUiConfigurationUseCase
import com.example.mumble.domain.usecase.ReadUiMessageUseCase
import com.example.mumble.domain.usecase.ReadWifiConnectionStateUseCase
import com.example.mumble.domain.usecase.SetUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateCurrentUsersNicknameUseCase
import com.example.mumble.domain.usecase.UpdateUiConfigurationUseCase
import com.example.mumble.domain.usecase.UpdateWifiConnectionStateUseCase
import com.example.mumble.utils.validator.FieldValidator
import com.example.mumble.utils.validator.impl.NicknameFieldValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideGetCurrentUserUseCase(repository: IChatRepository): ReadCurrentUserUseCase {
        return ReadCurrentUserUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSetNicknameUseCase(repository: IChatRepository): UpdateCurrentUsersNicknameUseCase {
        return UpdateCurrentUsersNicknameUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetWifiConnectionUseCase(repository: IConnectivityRepository): ReadWifiConnectionStateUseCase {
        return ReadWifiConnectionStateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSetWifiConnectionUseCase(repository: IConnectivityRepository): UpdateWifiConnectionStateUseCase {
        return UpdateWifiConnectionStateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideNicknameFieldValidator(): FieldValidator {
        return NicknameFieldValidator()
    }

    @Singleton
    @Provides
    fun provideSetUiMessageUseCase(repository: IUiRepository): SetUiMessageUseCase {
        return SetUiMessageUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUiMessageUseCase(repository: IUiRepository): ReadUiMessageUseCase {
        return ReadUiMessageUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllUsersOnlineUseCase(repository: IChatRepository): ReadAllUsersOnlineUseCase {
        return ReadAllUsersOnlineUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideReadUiConfigurationUseCase(repository: IUiRepository): ReadUiConfigurationUseCase {
        return ReadUiConfigurationUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateUiConfigurationUseCase(repository: IUiRepository): UpdateUiConfigurationUseCase {
        return UpdateUiConfigurationUseCase(repository)
    }
}
