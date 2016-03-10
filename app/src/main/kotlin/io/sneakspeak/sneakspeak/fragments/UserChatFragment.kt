package io.sneakspeak.sneakspeak.fragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.format.Time
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sneakspeak.sneakspeak.MainActivity
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.adapters.ChatAdapter
import io.sneakspeak.sneakspeak.data.Message
import io.sneakspeak.sneakspeak.managers.SettingsManager
import io.sneakspeak.sneakspeak.receiver.MessageResultReceiver
import kotlinx.android.synthetic.main.fragment_chat.*
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*


class UserChatFragment : Fragment(), View.OnClickListener, MessageResultReceiver.Receiver {

    companion object {
        var messageReceiver = MessageResultReceiver(Handler())
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        adapter.addMessage(Message("joku pälli", resultData?.toString() ?: "lol", "just ny"))
    }

    lateinit var adapter: ChatAdapter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, bundle: Bundle?)
        = inflater?.inflate(R.layout.fragment_chat, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        sendButton.setOnClickListener(this)
        adapter = ChatAdapter(listOf(Message("joku", "lol", "tänään"),
                Message("joku", "lol", "eilen")))

        messageList.adapter = adapter
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        manager.stackFromEnd = true
        messageList.layoutManager = manager

    }

    override fun onClick(button: View?) {
        if (messageText.text.isEmpty()) return

        val df = SimpleDateFormat("HH:mm:ss");
        val time = df.format(Calendar.getInstance().getTime());
        adapter.addMessage(Message(SettingsManager.getUsername(),
                messageText.text.toString(), time))
        messageText.text.clear()
        messageList.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onResume() {
        super.onResume()
        messageReceiver.receiver = this
    }

    override fun onPause() {
        super.onPause()
        messageReceiver.receiver = null
    }
}
