package io.sneakspeak.sneakspeak.managers

import android.content.SharedPreferences
import android.preference.PreferenceManager
import io.sneakspeak.sneakspeak.SneakSpeak


object SettingsManager {

    val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(SneakSpeak.context)
    }

    fun getAddress() = prefs.getString("address", "")

    fun getPort() = prefs.getString("port", "")

    fun getUsername() = prefs.getString("userName", "")

    fun getServerName() = prefs.getString("serverName", "")

    fun saveLoginInfo(address: String, port: String, serverName: String, userName: String) = with(prefs.edit()) {
        putString("address", address)
        putString("port", port)
        putString("serverName", serverName)
        putString("userName", userName)
        apply()
    }
}