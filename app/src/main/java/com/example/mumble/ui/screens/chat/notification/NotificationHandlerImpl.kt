package com.example.mumble.ui.screens.chat.notification

import android.app.Notification
import android.app.Notification.DEFAULT_SOUND
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.example.mumble.R
import com.example.mumble.services.ChatService
import com.example.mumble.ui.model.Message
import com.example.mumble.utils.extensions.getNotificationManager
import javax.inject.Inject

class NotificationHandlerImpl @Inject constructor(
    private val context: Context
) : NotificationHandler {

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.app_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_MAX
            val channel = NotificationChannel(ChatService.CHANNEL_ID, name, importance).apply {
                description = descriptionText
                setShowBadge(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            // Register the channel with the system
            val notificationManager = context.getNotificationManager()
            notificationManager.createNotificationChannel(channel)
        }
    }

    // TODO: Change notification small icon color, since it is hardcoded to white, therefore
    //  can not be shown when light theme is on.
    override fun showNotification(message: Message) {
        val builder = NotificationCompat.Builder(context, "MUMBLE")
            .setContentTitle(message.creator.username)
            .setContentText(message.content)
            .setDefaults(DEFAULT_SOUND) // Important for heads-up notification
            .setSmallIcon(R.drawable.ic_notification_bagde)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.ic_notification_bagde
                )
            )
            .setContentIntent(getOpenNotificationIntent(message))
            .setStyle(NotificationCompat.BigTextStyle().bigText(message.content))
            .setPriority(NotificationCompat.PRIORITY_MAX)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(message.hashCode(), builder.build())
        }
    }

    private fun getOpenNotificationIntent(message: Message): PendingIntent {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(
            Intent.ACTION_VIEW,
            "myapp://mumble.com/chat=${message.id}".toUri() // <-- Notice this
        )

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, FLAG_IMMUTABLE)
        }
    }
}
