package io.sneakspeak.sneakspeak.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.fragments.RegisterFragment
import kotlinx.android.synthetic.main.activity_main.*


class RegisterActivity : AppCompatActivity() {
    val TAG = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolbar)

        val action = supportFragmentManager.beginTransaction()
        action.replace(R.id.fragmentHolder, RegisterFragment())
        action.commit()
    }
}