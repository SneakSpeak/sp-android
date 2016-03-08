package io.sneakspeak.sneakspeak.gcm

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.support.v4.os.ResultReceiver
import android.util.Log
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.UserResultReceiver
import io.sneakspeak.sneakspeak.managers.HttpManager
import io.sneakspeak.sneakspeak.managers.SettingsManager
import org.jetbrains.anko.async
import org.jetbrains.anko.intentFor
import org.json.JSONObject
import java.util.*

/**
 * Class used to register to a GCM-server as a client.
 */
class RegistrationIntentService : IntentService("SneakIntent") {

    val TAG = "RegistrationIntentService"
    var receiver: ResultReceiver? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        receiver = intent?.getParcelableExtra("resultReceiverTag")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent) {
        val iID = InstanceID.getInstance(this)
        val token = iID.getToken(getString(R.string.gcm_defaultSenderId),
                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null)

        val address = SettingsManager.getAddress()
        val port = SettingsManager.getPort()
        val username = SettingsManager.getUsername()

        val regUrl = "http://$address:$port/api/user/register"
        val json = JSONObject()

        json.put("username", username)
        json.put("token", token)

        Log.d(TAG, "Sending registration.")

        val users: ArrayList<String>

        try {
            users = HttpManager.register(regUrl, json)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        Log.d(TAG, users.toString())

        val bundle = Bundle()
        bundle.putStringArrayList("userList", users)
        receiver?.send(0, bundle)
    }
}