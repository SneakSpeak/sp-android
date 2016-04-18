package io.sneakspeak.sneakspeak.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.data.Channel
import io.sneakspeak.sneakspeak.fragments.ChatFragment
import io.sneakspeak.sneakspeak.managers.HttpManager
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.async
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.withArguments
import org.jetbrains.anko.uiThread


class ChatActivity : AppCompatActivity() {

    val TAG = "ChatActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val user = intent.extras.getString("name")
        val channel = intent.extras.getSerializable("channel") as Channel?

        val frag = if (channel == null) {
            toolbar.title = "Private chat"
            toolbar.subtitle = user
            ChatFragment(user)
        } else {
            toolbar.title = "#${channel.name}"
            val dialog = indeterminateProgressDialog("Loading participant list")
            async() {
                val participants = HttpManager.getParticipants(channel)

                uiThread {
                    toolbar.subtitle = participants.toString() //channel.participants.toString()
                    dialog.dismiss()
                }

            }

            ChatFragment(channel)
        }

        setSupportActionBar(toolbar)

        val action = supportFragmentManager.beginTransaction()
        action.replace(R.id.fragmentHolder, frag)
        action.commit()
    }
}