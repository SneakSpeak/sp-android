package io.sneakspeak.sneakspeak.fragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.format.Time
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.gcm.GoogleCloudMessaging
import io.sneakspeak.sneakspeak.activities.MainActivity
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.SneakSpeak
import io.sneakspeak.sneakspeak.adapters.ChatAdapter
import io.sneakspeak.sneakspeak.data.Message
import io.sneakspeak.sneakspeak.managers.SettingsManager
import io.sneakspeak.sneakspeak.receiver.MessageResultReceiver
import kotlinx.android.synthetic.main.fragment_chat.*
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicInteger


class ChatFragment(user: String) : Fragment(), View.OnClickListener, MessageResultReceiver.Receiver {

    val chatUser = user

    companion object {
        var messageReceiver = MessageResultReceiver(Handler())
    }

    val msgId = AtomicInteger()
    val gcm = GoogleCloudMessaging.getInstance(SneakSpeak.context);
    lateinit var adapter: ChatAdapter

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        if (resultData == null) return

        adapter.addMessage(Message(resultData.getString("sender"),
                resultData.getString("message"), resultData.getString("time")))
        messageList.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, bundle: Bundle?)
            = inflater?.inflate(R.layout.fragment_chat, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        sendButton.setOnClickListener(this)
        adapter = ChatAdapter()

        messageList.adapter = adapter
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        manager.stackFromEnd = true
        messageList.layoutManager = manager

    }

    override fun onClick(button: View?) {
        if (messageText.text.isEmpty()) return

        val df = SimpleDateFormat("HH:mm:ss");
        val time = df.format(Calendar.getInstance().time);

        adapter.addMessage(Message(SettingsManager.getUsername(),
                messageText.text.toString(), time))

        messageList.scrollToPosition(adapter.itemCount - 1)

        // Todo: get the ID and receiver from somewhere
        val SENDER_ID = "943308880121"
        val data = Bundle()
        data.putString("receiver", chatUser)
        data.putString("message", messageText.text.toString())
        val id = Integer.toString(msgId.incrementAndGet())
        gcm.send("$SENDER_ID@gcm.googleapis.com", id, data)

        messageText.text.clear()
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
