package io.sneakspeak.sneakspeak

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import io.sneakspeak.sneakspeak.R.layout.activity_main
import io.sneakspeak.sneakspeak.fragments.LoginFragment
import io.sneakspeak.sneakspeak.gcm.RegistrationIntentService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor


class MainActivity : AppCompatActivity() {

    companion object {

        lateinit var ctx: Context;
        lateinit var fm: FragmentManager

        fun switchScreen() {

        }

        fun connectServer(): Boolean {
            try {
                ctx.startService(ctx.intentFor<RegistrationIntentService>())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return true
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        ctx = this
        fm = supportFragmentManager

        toolbar.subtitle = "Shhhh"
        setSupportActionBar(toolbar)

        val frag = LoginFragment()

        val action = fm.beginTransaction()
        action.replace(R.id.fragmentHolder, frag)
        action.commit()
    }
}
