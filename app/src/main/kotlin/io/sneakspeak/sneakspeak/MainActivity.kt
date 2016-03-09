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
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor


class MainActivity : AppCompatActivity(), UserResultReceiver.Receiver {

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        Log.d(TAG, "Received: $resultData")

        // Todo: save users and channels from data to persistent storage or something

        // 0: success
        if (resultCode == 0) {
            if (currentFragment is RegisterFragment)
                (currentFragment as RegisterFragment).sendResult("Rekisteröityminen onnistui.")
            switchScreen(UserChatFragment())
        }
        // Failure
        else {
            if (currentFragment is RegisterFragment)
                (currentFragment as RegisterFragment).sendResult("Rekisteröityminen ei onnistunut.")
        }
    }

    companion object {

        val TAG = "MainActivity"

        lateinit var ctx: Context;
        lateinit var fm: FragmentManager
        lateinit var currentFragment: Fragment


        var resultReceiver = UserResultReceiver(Handler())

        fun connectServer() {
            try {
                val intent = ctx.intentFor<RegistrationIntentService>()
                intent.putExtra("resultReceiverTag", resultReceiver)
                ctx.startService(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

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

        ctx = this
        fm = supportFragmentManager

        toolbar.subtitle = "Shhhh"
        setSupportActionBar(toolbar)

        switchScreen(RegisterFragment())
    }

    override fun onResume() {
        super.onResume()
        resultReceiver.receiver = this
    }

    override fun onPause() {
        super.onPause()
        resultReceiver.receiver = null
    }
}
