package com.example.mumble.data.repository.source.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.mumble.domain.repository.source.preferences.IUserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IUserPreferencesImpl(
    private val preferences: DataStore<Preferences>
) : IUserPreferences {
    override suspend fun setIsOnboarded(value: Boolean) {
        preferences.edit {
            it[IS_ONBOARDED] = value
        }
    }

    override fun getIsOnboarded(): Flow<Boolean> {
        return preferences.data.map { it[IS_ONBOARDED] ?: false }
    }

    private companion object {
        val IS_ONBOARDED = booleanPreferencesKey("isOnboarded")
    }
}
