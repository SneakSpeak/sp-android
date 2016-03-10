package io.sneakspeak.sneakspeak.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.sneakspeak.sneakspeak.MainActivity
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.containsText
import io.sneakspeak.sneakspeak.managers.SettingsManager
import kotlinx.android.synthetic.main.fragment_register.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.toast

class RegisterFragment : Fragment(), View.OnClickListener {

    val TAG = "LoginFragment"
    var dialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        serverAddress.setText(SettingsManager.getAddress())
        serverPort.setText(SettingsManager.getPort())
        username.setText(SettingsManager.getUsername())

        registerButton.setOnClickListener(this)
    }


    fun displayResult(message: String) {
        Log.d(TAG, message)
        dialog?.dismiss()
        toast(message)
    }


    override fun onClick(view: View?) {
        if (!serverAddress.containsText()) {
            toast("Address can't be empty")
        } else if (!serverPort.containsText()) {
            toast("Port can't be empty")
        } else if (!username.containsText()) {
            toast("Username can't be empty")
        } else {

            SettingsManager.saveLoginInfo(
                    serverAddress.text.toString(),
                    serverPort.text.toString(),
                    username.text.toString())

            dialog = indeterminateProgressDialog("Connecting to ${serverAddress.text.toString()}")
            dialog?.setCancelable(false)

            async() {
                MainActivity.connectServer()
            }
        }
    }
}

