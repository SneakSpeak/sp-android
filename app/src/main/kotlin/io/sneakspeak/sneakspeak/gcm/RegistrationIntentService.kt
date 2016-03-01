package io.sneakspeak.sneakspeak.gcm

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.managers.HttpManager
import io.sneakspeak.sneakspeak.managers.SettingsManager
import org.jetbrains.anko.async
import org.jetbrains.anko.intentFor
import org.json.JSONObject

/**
 * Class used to register to a GCM-server as a client.
 */
class RegistrationIntentService : IntentService("SneakIntent") {

    val TAG = "RegistrationIntentService"

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

        HttpManager.post(regUrl, json)

//        val data = Bundle()
//
//        val gcm = GoogleCloudMessaging.getInstance(this)
//        val senderId = getString(R.string.gcm_defaultSenderId)
//        data.putString("my_message", "Hello World")
//        data.putString("my_action","SAY_HELLO")
//        val id = Integer.toString(2);
//        gcm.send(senderId + "@gcm.googleapis.com", id, data);
    }
}