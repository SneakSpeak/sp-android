package io.sneakspeak.sneakspeak.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.fragments.ChatFragment
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    val TAG = "ChatActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val user = intent.extras.getString("name")
        toolbar.subtitle = user
        setSupportActionBar(toolbar)

        val action = supportFragmentManager.beginTransaction()
        action.replace(R.id.fragmentHolder, ChatFragment(user))
        action.commit()
    }
}