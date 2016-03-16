package io.sneakspeak.sneakspeak.gcm

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.support.v4.os.ResultReceiver
import android.util.Log
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.receiver.UserResultReceiver
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
        // Fetch server GCM-key

        // Todo: separate different server data
        val address = SettingsManager.getAddress()
        val port = SettingsManager.getPort()
        val username = SettingsManager.getUsername()

        val baseUrl = "http://$address:$port"

        val gcmKey = try {
            HttpManager.getGcmKey(baseUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Failed to request GCM key.")
            receiver?.send(1, null)
            return
        }

        // Create token and send it to server
        val iID = InstanceID.getInstance(this)
        val token = iID.getToken(gcmKey,
                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null)

        val registerPayload = JSONObject()
        registerPayload.put("username", username)
        registerPayload.put("token", token)

        val users = try {
            HttpManager.register(baseUrl, registerPayload)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Failed to register to server.")
            receiver?.send(2, null)
            return
        }

        Log.d(TAG, "Register successful, users: $users")
        val bundle = Bundle()
        bundle.putStringArrayList("userList", users)
        receiver?.send(0, bundle)
    }
}