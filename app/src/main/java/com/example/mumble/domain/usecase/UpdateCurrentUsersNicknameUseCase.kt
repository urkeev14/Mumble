package com.example.mumble.domain.usecase

import com.example.mumble.domain.repository.IChatRepository
import com.example.mumble.ui.screens.introduction.IntroductionScreen
import javax.inject.Inject

/**
 * Saves created nickname to repository.
 * This use-case is triggered once the valid nickname is created and users clicks on 'Go'
 * button on [IntroductionScreen]
 *
 * @property repository repository that provides setter method for nickname
 */
class UpdateCurrentUsersNicknameUseCase @Inject constructor(
    private val repository: IChatRepository
) {
    suspend operator fun invoke(nickname: String) {
        repository.updateCurrentUsersNickname(nickname)
    }
}
