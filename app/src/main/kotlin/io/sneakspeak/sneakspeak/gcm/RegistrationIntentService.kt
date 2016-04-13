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
    lateinit var receiver: ResultReceiver
    lateinit var serverBundle: Bundle

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        receiver = intent.getParcelableExtra("resultReceiverTag")
        serverBundle = intent.getBundleExtra("serverBundle")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent) {

        // Fetch server GCM-key
        val address = serverBundle.get("address")
        val port = serverBundle.get("port")

        val baseUrl = "http://$address:$port"

        val gcmKey = try {
            HttpManager.getGcmKey(baseUrl)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to request GCM key.")
            e.printStackTrace()
            receiver.send(1, null)
            return
        }

        // Create token and send it to server
        val iID = InstanceID.getInstance(this)
        val token = try {
            iID.getToken(gcmKey.replace("\"", ""),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create token")
            e.printStackTrace()
            receiver.send(1, null)
            return
        }

        val registerPayload = JSONObject()
        registerPayload.put("username", serverBundle.get("username"))
        registerPayload.put("token", token)

        val users = try {
            HttpManager.register(baseUrl, registerPayload)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to register to server.")
            e.printStackTrace()
            receiver.send(2, null)
            return
        }

        Log.d(TAG, "Register successful, users: $users")
        val bundle = Bundle()
        bundle.putStringArrayList("users", users)
        bundle.putString("token", token)
        receiver.send(0, bundle)
    }
}