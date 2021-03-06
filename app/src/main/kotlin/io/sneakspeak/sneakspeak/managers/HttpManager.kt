package io.sneakspeak.sneakspeak.managers

import android.util.Log
import io.sneakspeak.sneakspeak.data.Channel
import io.sneakspeak.sneakspeak.data.Server
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

    fun sendMessage(server: Server, receiver: String, message: String) {

        Log.d(TAG, "Sending message $message to user $receiver")

        val jsonObject = JSONObject()
        jsonObject.put("token", server.token)
        jsonObject.put("message", message)

        val url = "${server.url()}/api/user/$receiver/message"
        val body = RequestBody.create(JSON, jsonObject.toString())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        val response = httpClient.newCall(request).execute()

        Log.d(TAG, "Got response: $response")
        response.body().close()
    }



    fun getGcmKey(server: String): String {

        Log.d(TAG, "Asking for GCM key")

        val url = "$server/api/gcm/key"

        val request = Request.Builder()
                .url(url)
                .get()
                .build()

        val response = httpClient.newCall(request).execute()

        Log.d(TAG, "Got response: $response")

        val key = response.body().string()
        response.body().close()

        Log.d(TAG, "Received key: $key")

        return key
    }

    fun register(server: String, json: JSONObject): ArrayList<String> {

        Log.d(TAG, "Sending register request")

        val url = "$server/api/user/register"

        val body = RequestBody.create(JSON, json.toString())

        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        val response = httpClient.newCall(request).execute()
        Log.d(TAG, "Got response: $response")

        val users = parseUsers(response.body().string())
        response.body().close()
        return users
    }

    fun getUsers(server: Server): List<String> {

        Log.d(TAG, "Requesting user list")

        // Todo: fix
        // ?token={token}
        val url = "${server.url()}/api/user/list?token=${server.token}"

        val request = Request.Builder()
                .url(url)
                .get()
                .build()

        val response = httpClient.newCall(request).execute()
        Log.d(TAG, "Got response: $response")

        val users = parseUsers(response.body().string())
        response.body().close()
        return users
    }

    private fun parseUsers(json: String): ArrayList<String> {
        val users = ArrayList<String>()
        val usersJson = JSONArray(json)

        for (i in 0..usersJson.length()-1)
            users.add(usersJson.getString(i))

        return users
    }

    fun getPublicChannels(server: Server): List<Channel> {

        Log.d(TAG, "Requesting public channel list")

        //val url = "${server.url()}/api/user/channels?token=${server.token}"
        val url = "${server.url()}/api/channel"

        val request = Request.Builder()
                .url(url)
                .get()
                .build()

        val response = httpClient.newCall(request).execute()
        Log.d(TAG, "Got response: $response")

        val channels = try {
            JsonManager.deserializeChannels(response.body().string())
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
        response.body().close()
        return channels
    }

    fun joinOrCreateChannel(server: Server, channelName: String, public: Boolean): Channel? {
        Log.d(TAG, "Joining channel $channelName")

        val url = "${server.url()}/api/channel/joinOrCreate"

        val jsonObject = JSONObject()
        jsonObject.put("token", server.token)
        jsonObject.put("name", channelName)
        jsonObject.put("public", public)

        val body = RequestBody.create(JSON, jsonObject.toString())

        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        val response = httpClient.newCall(request).execute()
        Log.d(TAG, "Got response: $response")

        val channels = try {
            JsonManager.deserializeOneChannel(response.body().string())
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        response.body().close()
        return channels
    }

    fun sendChannelMessage(server: Server, channel: Channel, message: String) {
        Log.d(TAG, "Sending message $message to channel $channel")

        val jsonObject = JSONObject()
        jsonObject.put("token", server.token)
        jsonObject.put("message", message)

        val url = "${server.url()}/api/channel/${channel.id}/message"
        val body = RequestBody.create(JSON, jsonObject.toString())

        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        val response = httpClient.newCall(request).execute()

        Log.d(TAG, "Got response: $response")
        response.body().close()
    }

    fun getJoinedChannels(server: Server): List<Channel> {

            Log.d(TAG, "Requesting joined channel list")

            val url = "${server.url()}/api/user/channels?token=${server.token}"
            // val url = "${server.url()}/api/channel"

            val request = Request.Builder()
                    .url(url)
                    .get()
                    .build()

            val response = httpClient.newCall(request).execute()
            Log.d(TAG, "Got response: $response")

            val channels = try {
                JsonManager.deserializeChannels(response.body().string())
            } catch (e: Exception) {
                e.printStackTrace()
                return emptyList()
            }
            response.body().close()
            return channels

    }

    fun getParticipants(channel: Channel): List<String> {
        Log.d(TAG, "Requesting participant list for channel $channel")

        val server = DatabaseManager.getCurrentServer() ?: return listOf()

        val url = "${server.url()}/api/channel/${channel.id}/participants"
        // val url = "${server.url()}/api/channel"

        val request = Request.Builder()
                .url(url)
                .get()
                .build()

        val response = httpClient.newCall(request).execute()
        Log.d(TAG, "Got response: $response")

        val participants = try {
            JsonManager.deserializeUsers(response.body().string())
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
        response.body().close()
        return participants
    }


}
