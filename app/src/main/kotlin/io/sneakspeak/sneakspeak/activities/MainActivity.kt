package io.sneakspeak.sneakspeak.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sneakspeak.sneakspeak.adapters.MainFragmentAdapter
import io.sneakspeak.sneakspeak.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        setSupportActionBar(toolbar)

        viewPager.adapter = MainFragmentAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 3
        tabStrip.setupWithViewPager(viewPager)
    }
}
