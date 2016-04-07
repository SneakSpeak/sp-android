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

    val SERVER_KEY = "DB_SERVER_KEY"
    val CURRENT_SERVER_KEY = "DB_CURRENT_SERVER_KEY"

    fun addServer(server: Server) {
        openDatabase()
        lock.withLock {
            val servers = if (db!!.findKeys(SERVER_KEY).size > 0) {
                val serverJson = db!!.get(SERVER_KEY)
                JsonManager.deserializeServers(serverJson)
            } else {
                ArrayList()
            }
            servers.add(server)
            db!!.put(SERVER_KEY, JsonManager.serializeServers(servers))
        }
        closeDatabase()
    }

    fun getServers(): List<Server> {
        var servers = listOf<Server>()
        openDatabase()
        lock.withLock {
            if (db!!.findKeys(SERVER_KEY).size > 0) {
                val serverJson = db!!.get(SERVER_KEY)
                servers = JsonManager.deserializeServers(serverJson)
            }
        }
        closeDatabase()
        return servers
    }

    fun getCurrentServer(): Server? {
        var server: Server? = null
        openDatabase()
        lock.withLock {
            if (db!!.findKeys(CURRENT_SERVER_KEY).size > 0) {
                val serverJson = db!!.get(CURRENT_SERVER_KEY)
                server = JsonManager.deserializeCurrentServer(serverJson)
            }
        }
        closeDatabase()
        return server
    }

    fun setCurrentServer(server: Server) {
        openDatabase()
        lock.withLock {
            val serverJson = JsonManager.serializeCurrentServer(server)
            db!!.put(CURRENT_SERVER_KEY, serverJson)
        }
        closeDatabase()
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