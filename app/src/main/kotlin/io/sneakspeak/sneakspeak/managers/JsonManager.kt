package io.sneakspeak.sneakspeak.managers

import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.sneakspeak.sneakspeak.data.Channel
import io.sneakspeak.sneakspeak.data.Server
import org.json.JSONArray
import java.util.*


object  JsonManager {

    val TAG = "JsonManager"
    val mapper = jacksonObjectMapper()

    fun deserializeServers(str: String) = mapper.readValue<ArrayList<Server>>(str)

    fun serializeServers(servers: ArrayList<Server>) = mapper.writeValueAsString(servers)

    fun deserializeCurrentServer(str: String) = mapper.readValue<Server>(str)

    fun serializeCurrentServer(server: Server) = mapper.writeValueAsString(server)

    fun deserializeChannels(string: String) = mapper.readValue<List<Channel>>(string)

}