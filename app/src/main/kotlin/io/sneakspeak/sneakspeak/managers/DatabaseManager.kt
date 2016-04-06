package io.sneakspeak.sneakspeak.managers

import com.snappydb.DB
import com.snappydb.DBFactory
import io.sneakspeak.sneakspeak.SneakSpeak
import io.sneakspeak.sneakspeak.data.Server
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


object DatabaseManager {
    private val lock = ReentrantLock()
    private var db: DB? = null

    fun addServer(server: Server) {
        openDatabase()
        lock.withLock {
            var servers = ArrayList<Server>()
            if (db!!.findKeys("server").size > 0) {
                val serverJson = db!!.get("servers")
                if (serverJson != "")
                    servers = JsonManager.deserializeServers(serverJson)
            }
            servers.add(server)
            db!!.put("servers", servers)
        }
        closeDatabase()
    }

    fun getServers(): List<Server> {
        var servers = listOf<Server>()
        openDatabase()
        lock.withLock {
            if (db!!.findKeys("server").size > 0) {
                val serverJson = db!!.get("servers")
                servers = JsonManager.deserializeServers(serverJson)
            }
        }
        closeDatabase()
        return servers
    }

    private fun openDatabase() {
        lock.withLock {
            if (db == null || !db!!.isOpen)
                db = DBFactory.open(SneakSpeak.context)
        }
    }

    private fun closeDatabase() {
        lock.withLock {
            if (db != null && db!!.isOpen)
                db?.close()
        }
    }

}