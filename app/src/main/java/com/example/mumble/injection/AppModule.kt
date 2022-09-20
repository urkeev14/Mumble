package com.example.mumble.injection

import android.content.Context
import android.net.nsd.NsdManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.mumble.domain.usecase.SetUiMessageUseCase
import com.example.mumble.domain.usecase.UpdateUiConfigurationUseCase
import com.example.mumble.ui.IUiManager
import com.example.mumble.ui.UiManager
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
}
