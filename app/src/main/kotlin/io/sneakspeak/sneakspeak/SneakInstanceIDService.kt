package io.sneakspeak.sneakspeak

import android.app.IntentService
import android.content.Intent
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import org.jetbrains.anko.async
import org.json.JSONObject

/**
 * Class used to register to a GCM-server as a client.
 */
class SneakInstanceIDService : IntentService("SneakIntent") {

    override fun onHandleIntent(intent: Intent) {
        val iID = InstanceID.getInstance(this)
        async() {
            val token: String = iID.getToken(getString(R.string.server_key),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null)

            val regUrl = "http://sneakspeak.dy.fi:3000/api/user/register"
            val json = JSONObject()

            json.put("username", "meitsi")
            json.put("password", "lolJoqPassu")
            json.put("token", token)

            log(token)

            HttpManager.post(regUrl, json)
        }
    }
}