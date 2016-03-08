package io.sneakspeak.sneakspeak.managers

import android.util.Log
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

object HttpManager {
    val TAG = "HttpManager"
    val httpClient = OkHttpClient()
    val JSON = MediaType.parse("application/json; charset=utf-8")


    fun register(url: String, json: JSONObject): ArrayList<String> {

        Log.d(TAG, json.toString())

        val body = RequestBody.create(JSON, json.toString())

        var request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        val response = httpClient.newCall(request).execute()
        Log.d(TAG, response.toString())
        val resBody = response.body().string()

        val users = ArrayList<String>()
        val usersJson = JSONArray(resBody)

        // response.body().close()

        for (i in 0..usersJson.length()-1)
            users.add(usersJson.getString(i))

        return users
    }
}
