package io.sneakspeak.sneakspeak.managers

import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.sneakspeak.sneakspeak.data.Server
import org.json.JSONArray
import java.util.*


object  JsonManager {

    val TAG = "JsonManager"
    val mapper = jacksonObjectMapper()

    fun deserializeServers(str: String): ArrayList<Server> {
        Log.d(TAG, "str:$str, pituus ${str.length}")

        // For some reason some strings don't contain printable but have a non-zero
        // length property, so this is just a quick solution for this...
        if (str.length < 10) return ArrayList()

        return mapper.readValue<ArrayList<Server>>(str)
        // fuck you jackson

//        val servers = ArrayList<Server>()
//
//        val jsonArr = JSONArray(str)
//        for (i in 0..jsonArr.length()-1) {
//            val it = jsonArr.getJSONObject(i)
//            servers.add(Server(it.getString("address"), it.getString("port"), it.getString("name"),
//                    it.getString("token"), it.getString("username")))
//        }
//
//        return servers
    }

    fun serializeServers(servers: ArrayList<Server>) = mapper.writeValueAsString(servers)

}