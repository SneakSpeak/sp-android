package io.sneakspeak.sneakspeak

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

class SneakSpeak : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
