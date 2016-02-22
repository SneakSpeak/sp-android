package io.sneakspeak.sneakspeak

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.sneakspeak.sneakspeak.R.layout.app_bar_main
import kotlinx.android.synthetic.main.app_bar_main.*

import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.intentFor


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(app_bar_main)
        toolbar.subtitle = "Shhh"
        setSupportActionBar(toolbar)

        connectButton.setOnClickListener(this)
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
        startService(intentFor<SneakInstanceIDService>())
    }
}
