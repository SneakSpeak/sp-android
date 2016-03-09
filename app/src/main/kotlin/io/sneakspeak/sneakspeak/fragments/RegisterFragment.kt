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
import io.sneakspeak.sneakspeak.containsText
import io.sneakspeak.sneakspeak.managers.SettingsManager
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.toast

class RegisterFragment : Fragment() {

    val TAG = "LoginFragment"
    private lateinit var fragUi: RegisterUI<RegisterFragment>

    fun sendResult(message: String) {
        Log.d(TAG, message)
        fragUi.dialog.dismiss()
        toast(message)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragUi = RegisterUI<RegisterFragment>()
        return fragUi.createView(AnkoContext.create(activity, this))
    }

    /**
     * Create Fragment UI using Anko DSL instead of XML.
     */
    private class RegisterUI<T> : AnkoComponent<T> {
        lateinit var address: TextView
        lateinit var port: TextView
        lateinit var username: TextView
        lateinit var dialog: ProgressDialog

        override fun createView(ui: AnkoContext<T>) = with(ui) {
            verticalLayout {
                padding = 16

                textView("Input your server information") { textSize = 20f }

                gridLayout {
                    columnCount = 2

                    textView("IP Address")
                    address = editText(SettingsManager.getAddress()) {
                        inputType = InputType.TYPE_CLASS_TEXT
                    }.lparams { width = matchParent }

                    textView("Port")
                    port = editText(SettingsManager.getPort()) {
                        inputType = InputType.TYPE_CLASS_NUMBER
                    }.lparams { width = matchParent }

                    textView("Username")
                    username = editText(SettingsManager.getUsername()) {
                        inputType = InputType.TYPE_CLASS_TEXT
                    }.lparams { width = matchParent }
                }

                button("Register") {
                    onClick {
                        if (!address.containsText()) {
                            toast("Address can't be empty")
                        } else if (!port.containsText()) {
                            toast("Port can't be empty")
                        } else if (!username.containsText()) {
                            toast("Username can't be empty")
                        } else {

                            SettingsManager.saveLoginInfo(
                                    address.text.toString(),
                                    port.text.toString(),
                                    username.text.toString())

                            dialog = indeterminateProgressDialog("Connecting to ${address.text.toString()}")
                            dialog.setCancelable(false)

                            async() {
                                MainActivity.connectServer()
                            }
                        }

                    }

                }
            }
        }
    }
}

