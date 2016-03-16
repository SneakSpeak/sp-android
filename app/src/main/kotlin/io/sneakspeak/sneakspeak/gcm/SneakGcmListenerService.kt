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
import io.sneakspeak.sneakspeak.MainActivity
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.fragments.UserChatFragment
import io.sneakspeak.sneakspeak.receiver.MessageResultReceiver
import java.text.SimpleDateFormat
import java.util.*


class SneakGcmListenerService : GcmListenerService() {

    val TAG = "GcmListenerService"

    override fun onMessageReceived(from: String?, data: Bundle?) {
        // super.onMessageReceived(from, data)
        Log.d(TAG, data?.toString())

        Log.d(TAG, "Message received, do something about it.")

        val df = SimpleDateFormat("HH:mm:ss");
        val time = df.format(Calendar.getInstance().time);

        val bundle = Bundle()
        val msg = data?.getBundle("notification")
        bundle.putString("sender", msg?.getString("title"))
        bundle.putString("message", msg?.getString("body"))
        bundle.putString("time", time)

        UserChatFragment.messageReceiver.send(0, bundle)

//      sendNotification(title, message)
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