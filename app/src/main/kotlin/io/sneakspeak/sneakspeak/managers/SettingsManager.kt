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

    fun getUsername() = prefs.getString("username", "")

    fun saveLoginInfo(address: String, port: String, username: String) = with(prefs.edit()) {
        putString("address", address)
        putString("port", port)
        putString("username", username)
        apply()
    }

    fun setServerKey(key: String) = with(prefs.edit()) {
        putString("serverKey", key)
        apply()
    }

    fun setToken(token: String) = with(prefs.edit()) {
        putString("token", token)
        apply()
    }
}