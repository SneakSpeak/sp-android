package io.sneakspeak.sneakspeak.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.containsText
import io.sneakspeak.sneakspeak.data.Server
import io.sneakspeak.sneakspeak.gcm.RegistrationIntentService
import io.sneakspeak.sneakspeak.managers.DatabaseManager
import io.sneakspeak.sneakspeak.managers.SettingsManager
import io.sneakspeak.sneakspeak.receiver.UserResultReceiver
import kotlinx.android.synthetic.main.fragment_register.*
import org.jetbrains.anko.async
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.toast
import java.net.Inet4Address

class RegisterFragment : Fragment(), UserResultReceiver.Receiver, View.OnClickListener {

    val TAG = "RegisterFragment"

    // val msgId = AtomicInteger()
    // val gcm = GoogleCloudMessaging.getInstance(SneakSpeak.context);

    var dialog: ProgressDialog? = null

    var resultReceiver = UserResultReceiver(Handler())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultReceiver.receiver = this
    }

    override fun onPause() {
        super.onPause()
        resultReceiver.receiver = null
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        serverAddress.setText(SettingsManager.getAddress())
        serverPort.setText(SettingsManager.getPort())
        serverName.setText(SettingsManager.getServerName())
        userName.setText(SettingsManager.getUsername())

        registerButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (!serverAddress.containsText()) {
            toast("Address can't be empty")
        } else if (!serverPort.containsText()) {
            toast("Port can't be empty")
        } else if (!userName.containsText()) {
            toast("Username can't be empty")
        } else {

            dialog = indeterminateProgressDialog("Connecting to ${serverAddress.text.toString()}")
            dialog?.setCancelable(false)

            // Save latest info
            SettingsManager.saveLoginInfo(
                    serverAddress.text.toString(),
                    serverPort.text.toString(),
                    serverName.text.toString(),
                    userName.text.toString())

            // Check if the server is already added
            val currentServers = DatabaseManager.getServers()

            var ca = serverAddress.text.toString()
            val cp = serverPort.text.toString()

            async() {
                // Ip address of the server
                ca = Inet4Address.getByName(ca).toString().split("/").last()

                for ((address, port) in currentServers) {
                    if (ca == address && cp == port) {
                        onUiThread {
                            toast("You are already registered to this server.")
                            dialog?.dismiss()
                        }
                        return@async
                    }
                }

                connectServer()
            }
        }
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        Log.d(TAG, "Received: $resultData")

        dialog?.dismiss()

        val token = resultData?.getString("token")
        val users = resultData?.getStringArrayList("users")

        // Todo: save users and channels from data to persistent storage or something
        if (token == null || users == null) {
            toast("token tai userit on nulleja")
            return
        }


        // 0: success
        if (resultCode == 0) {
            toast("Rekisteröityminen onnistui.")

            async() {
//                val parts = Inet4Address.getByName(serverAddress.text.toString()).toString().split("/")
//                val addr = parts.last()

                // Choose server name
                val servname = if (serverName.text.toString() != "")
                    serverName.text.toString()
                else serverAddress.text.toString()
//                else if (parts.first() != "")
//                    parts.first()
//                else
//                    parts.last()

                val intent = Intent()
                val server = Server(
                        serverAddress.text.toString(),
                        serverPort.text.toString(),
                        servname,
                        token,
                        userName.text.toString())

                DatabaseManager.setCurrentServer(server)

                intent.putExtra("server", server)
                intent.putExtra("users", users)

                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()
            }
        }
        // Failure (or other results later on?
        else {
            toast("Rekisteröityminen ei onnistunut.")
        }
    }

    fun connectServer() {
        try {
            val intent = activity.intentFor<RegistrationIntentService>()
            intent.putExtra("resultReceiverTag", resultReceiver)

            val serverBundle = Bundle()
            with(serverBundle) {
                putString("address", serverAddress.text.toString())
                putString("port", serverPort.text.toString())
                putString("username", userName.text.toString())
            }
            intent.putExtra("serverBundle", serverBundle)

            activity.startService(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}

