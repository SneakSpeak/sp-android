package io.sneakspeak.sneakspeak.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.gcm.GoogleCloudMessaging
import io.sneakspeak.sneakspeak.MainActivity
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.SneakSpeak
import io.sneakspeak.sneakspeak.containsText
import io.sneakspeak.sneakspeak.gcm.RegistrationIntentService
import io.sneakspeak.sneakspeak.managers.SettingsManager
import io.sneakspeak.sneakspeak.receiver.UserResultReceiver
import kotlinx.android.synthetic.main.fragment_register.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.toast
import java.util.concurrent.atomic.AtomicInteger

class RegisterFragment : Fragment(), UserResultReceiver.Receiver, View.OnClickListener {

    val TAG = "RegisterFragment"

    val msgId = AtomicInteger()
    val gcm = GoogleCloudMessaging.getInstance(SneakSpeak.context);

    var dialog: ProgressDialog? = null

    var resultReceiver = UserResultReceiver(Handler())

    fun connectServer() {
        try {
            val intent = activity.intentFor<RegistrationIntentService>()
            intent.putExtra("resultReceiverTag", resultReceiver)
            activity.startService(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        serverAddress.setText(SettingsManager.getAddress())
        serverPort.setText(SettingsManager.getPort())
        username.setText(SettingsManager.getUsername())

        registerButton.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        if (!serverAddress.containsText()) {
            toast("Address can't be empty")
        } else if (!serverPort.containsText()) {
            toast("Port can't be empty")
        } else if (!username.containsText()) {
            toast("Username can't be empty")
        } else {

            // Save
            SettingsManager.saveLoginInfo(
                    serverAddress.text.toString(),
                    serverPort.text.toString(),
                    username.text.toString())

            dialog = indeterminateProgressDialog("Connecting to ${serverAddress.text.toString()}")
            dialog?.setCancelable(false)

//            async() {
//
//
//                try {
//
//
//            val SENDER_ID = "943308880121"
//            val data = Bundle()
//            data.putString("my_message", "Hello World")
//            data.putString("my_action","SAY_HELLO")
//            val id = Integer.toString(msgId.incrementAndGet())
//            gcm.send("$SENDER_ID@gcm.googleapis.com", id, data)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//
//            }
            async() {
                connectServer()
            }
        }
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        Log.d(MainActivity.TAG, "Received: $resultData")

        // Todo: save users and channels from data to persistent storage or something

        // 0: success
        if (resultCode == 0) {
            dialog?.dismiss()
            toast("Rekisteröityminen onnistui.")
            MainActivity.switchScreen(UserChatFragment())
        }
        // Failure (or other results later on?
        else {
            dialog?.dismiss()
            toast("Rekisteröityminen ei onnistunut.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultReceiver.receiver = this
    }

    override fun onPause() {
        super.onPause()
        resultReceiver.receiver = null
    }

}

