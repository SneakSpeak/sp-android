package io.sneakspeak.sneakspeak.gcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.google.android.gms.gcm.GcmListenerService
import io.sneakspeak.sneakspeak.activities.MainActivity
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.fragments.ChatFragment
import io.sneakspeak.sneakspeak.receiver.MessageResultReceiver
import java.text.SimpleDateFormat
import java.util.*


class SneakGcmListenerService : GcmListenerService() {

    val TAG = "SneakGcmListenerService"

    override fun onMessageSent(msgId: String?) {
        super.onMessageSent(msgId)
        Log.d(TAG, "Message sent with id: $msgId")
    }

    override fun onSendError(msgId: String?, error: String?) {
        super.onSendError(msgId, error)
        Log.e(TAG, "Error sending message: $msgId, error: $error")
    }

    override fun onMessageReceived(from: String?, data: Bundle?) {
        // super.onMessageReceived(from, data)
        Log.d(TAG, data?.toString())

        Log.d(TAG, "Message received, do something about it.")

        val df = SimpleDateFormat("HH:mm:ss");
        val time = df.format(Calendar.getInstance().time);

        val sender = data?.getString("title")
        val message = data?.getString("message")

        if (sender == null || message == null) return

        val bundle = Bundle()
        bundle.putString("sender", sender)
        bundle.putString("message", message)
        bundle.putString("time", time)

        val channelName = data?.getString("channelName")

        if (ChatFragment.messageReceiver != null && ChatFragment.messageReceiver?.receiver != null
            && ((ChatFragment.chatChannel != null && ChatFragment.chatChannel?.name == channelName)
                || (ChatFragment.chatUser != null && ChatFragment.chatUser == sender))) {
            // Play notification sound
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(applicationContext, notification);
            ringtone.play();

            ChatFragment.messageReceiver?.send(0, bundle)

        }
        else {
            sendNotification(sender, message)
        }
    }

    fun sendNotification(title: String, message: String) {
        try {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this)
                    .setContentTitle(title)
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