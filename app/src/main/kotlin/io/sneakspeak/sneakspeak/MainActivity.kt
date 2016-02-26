package io.sneakspeak.sneakspeak

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.sneakspeak.sneakspeak.R.layout.activity_main
import io.sneakspeak.sneakspeak.fragments.LoginFragment
import io.sneakspeak.sneakspeak.managers.SettingsManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.intentFor
import kotlin.jvm.javaClass


class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {

        lateinit var ctx: Context;
        lateinit var fm: FragmentManager

        fun switchScreen() {

        }

        fun connectServer(): Boolean {
            ctx.startService(ctx.intentFor<RegistrationIntentService>())
            //val service = Intent(ctx, javaClass<SneakInstanceIDService>())




            return false
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

        // connectButton.setOnClickListener(this)
    }




    override fun onClick(p0: View?) {
        /*val address = address.text.toString()
        val port = Integer.parseInt(port.text.toString())
        val route = route.text.toString()

        val url = "http://$address:$port$route"

        toast(url)

        val json = JSONObject()
        json.put("username", "meitsi")
        json.put("device", "supakey")
        json.put("password", "blob")

        async() {
            HttpManager.post(url, json)
        }*/

        // val service = Intent(this, javaClass<SneakInstanceIDService>())
        // startService(intentFor<SneakInstanceIDService>())
    }
}
