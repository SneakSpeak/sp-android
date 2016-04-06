package io.sneakspeak.sneakspeak.managers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.sneakspeak.sneakspeak.data.Server
import java.util.*


object  JsonManager {
    val mapper = jacksonObjectMapper()

    fun deserializeServers(str: String): ArrayList<Server> {
        println(str)
        val servers: ArrayList<Server> = mapper.readValue(str)
        return servers
    }
}