package com.example.mumble.injection

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
    fun provideNicknameFieldValidator(): FieldValidator {
        return NicknameFieldValidator()
    }
}
