package com.example.redboxapps.ui.backgroundtask


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.redboxapps.FCM_CHANNEL_ID
import com.example.redboxapps.FCM_CHANNEL_NAME
import com.example.redboxapps.FCM_NOTIF_ID
import com.example.redboxapps.R
import com.example.redboxapps.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val message = remoteMessage.notification?.body
        sendNotification(message)
    }

    private fun sendNotification(body: String?) {
        val channelId = FCM_CHANNEL_ID
        val channelName = FCM_CHANNEL_NAME

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationmanager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationBuilder.setChannelId(channelId)
            notificationmanager.createNotificationChannel(channel)
        }

        val notification = notificationBuilder.build()
        notificationmanager.notify(FCM_NOTIF_ID, notification)
    }

}
