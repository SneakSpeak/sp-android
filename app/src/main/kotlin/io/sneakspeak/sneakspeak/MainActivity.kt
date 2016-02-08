package io.sneakspeak.sneakspeak

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.json.JSONObject


class MainActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
        connectButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val address = address.text.toString()
        val port = Integer.parseInt(port.text.toString())
        val route = route.text.toString()

        val url = "http://$address:$port$route"

        toast(url)

        val json = JSONObject()
        json.put("username", "meitsi")
        json.put("device", "supakey")

        async() {
            HttpManager.post(url, json)
        }


    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
