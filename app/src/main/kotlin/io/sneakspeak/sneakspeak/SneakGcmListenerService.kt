package io.sneakspeak.sneakspeak

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.google.android.gms.gcm.GcmListenerService


class SneakGcmListenerService : GcmListenerService() {

    val TAG = "GcmListenerService"

    override fun onMessageReceived(from: String?, data: Bundle?) {
        // super.onMessageReceived(from, data)
        Log.d(TAG, data?.toString())

        sendNotification("Message received.")
    }

    override fun onMessageSent(msgId: String?) {
        //super.onMessageSent(msgId)
        Log.d(TAG, msgId)
        sendNotification("Message sent.")
    }

    fun sendNotification(message: String) {
        try {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this)
                    .setContentTitle("GCM Message")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}