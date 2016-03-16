package io.sneakspeak.sneakspeak

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.os.ResultReceiver
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.sneakspeak.sneakspeak.R.layout.activity_main
import io.sneakspeak.sneakspeak.fragments.RegisterFragment
import io.sneakspeak.sneakspeak.fragments.UserChatFragment
import io.sneakspeak.sneakspeak.gcm.RegistrationIntentService
import io.sneakspeak.sneakspeak.receiver.MessageResultReceiver
import io.sneakspeak.sneakspeak.receiver.UserResultReceiver
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor


class MainActivity : AppCompatActivity() {

    companion object {

        val TAG = "MainActivity"

        // lateinit var ctx: Context;
        lateinit var fm: FragmentManager
        lateinit var currentFragment: Fragment

        fun switchScreen(frag: Fragment) {
            currentFragment = frag

            val action = fm.beginTransaction()
            action.replace(R.id.fragmentHolder, currentFragment)
            action.commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        // ctx = this
        fm = supportFragmentManager

        setSupportActionBar(toolbar)

        switchScreen(RegisterFragment())
    }
}
