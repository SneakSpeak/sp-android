package io.sneakspeak.sneakspeak.managers

import io.sneakspeak.sneakspeak.log
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

object HttpManager {
    val httpClient = OkHttpClient()
    val JSON = MediaType.parse("application/json; charset=utf-8")



    fun post(url: String, json: JSONObject) {

        log(json.toString())

        val body = RequestBody.create(JSON, json.toString())

        var request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        try {
            val response = httpClient.newCall(request).execute()
            log(response.toString())
            response.body().close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
