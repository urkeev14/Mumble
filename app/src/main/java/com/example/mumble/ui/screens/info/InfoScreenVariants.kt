package com.example.mumble.ui.screens.info

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mumble.R

sealed class InfoScreenVariants(
    @DrawableRes val iconResId: Int,
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int,
    @StringRes val actionButton: Int? = null,
) {
    object EmptyScreen : InfoScreenVariants(
        iconResId = R.drawable.ic_empty_inbox,
        titleResId = R.string.empty_inbox_title,
        descriptionResId = R.string.empty_inbox_description
    )

    object NobodyAvailableScreen : InfoScreenVariants(
        iconResId = R.drawable.ic_alone,
        titleResId = R.string.nobody_online,
        descriptionResId = R.string.nobody_online_description
    )

    private object Onboarding {
        object EncryptedMessages : InfoScreenVariants(
            iconResId = R.drawable.ic_encrypted,
            titleResId = R.string.encrypted_messages_title,
            descriptionResId = R.string.encrypted_messages_description
        )

        object NoMessageStored : InfoScreenVariants(
            iconResId = R.drawable.ic_message_removed,
            titleResId = R.string.message_removed_title,
            descriptionResId = R.string.message_removed_description
        )

        object MessagesWillBeDeleted : InfoScreenVariants(
            iconResId = R.drawable.ic_no_storage,
            titleResId = R.string.no_storage_title,
            descriptionResId = R.string.no_storage_description,
            actionButton = R.string.done
        )
    }

    companion object {
        val onboardingScreens = listOf(
            Onboarding.EncryptedMessages, Onboarding.NoMessageStored, Onboarding.MessagesWillBeDeleted
        )
    }
}
