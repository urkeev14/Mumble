package com.example.mumble.domain.repository.source.preferences

import kotlinx.coroutines.flow.Flow

interface IUserPreferences {
    /**
     * Sets [Boolean] value that shows if a user is onboarded or not
     *
     * @param value indicator if a user is onboarded or not
     */
    suspend fun setIsOnboarded(value: Boolean = true)

    /**
     * Returns [Boolean] value that represents weather a user is onboarded or not
     *
     * @return true if onboarded, otherwise false
     */
    fun getIsOnboarded(): Flow<Boolean>
}
