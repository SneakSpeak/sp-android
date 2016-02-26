package io.sneakspeak.sneakspeak.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import io.sneakspeak.sneakspeak.MainActivity
import io.sneakspeak.sneakspeak.RegistrationIntentService
import io.sneakspeak.sneakspeak.managers.SettingsManager
import org.jetbrains.anko.*

class LoginFragment : Fragment() {
    companion object {

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            //= inflater?.inflate(R.layout.content_main, container, false)
            = LoginUI<Fragment>().createView(AnkoContext.create(activity, this))

    class LoginUI<T> : AnkoComponent<T> {
        lateinit var address: TextView
        lateinit var port: TextView
        lateinit var username: TextView

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

                button("Register / Login") {
                    onClick {
                        if (address.text.toString().replace("\\s+", "").length == 0) {
                            toast("Address can't be empty")
                        } else if (port.text.toString().replace("\\s+", "").length == 0) {
                            toast("Port can't be empty")
                        } else if (username.text.toString().replace("\\s+", "").length == 0) {
                            toast("Username can't be empty")
                        } else {

                            SettingsManager.saveLoginInfo(
                                    address.text.toString(),
                                    port.text.toString(),
                                    username.text.toString())

                            val dialog = indeterminateProgressDialog("Connecting to ${address.text.toString()}")
                            dialog.setCancelable(false)

                            async() {
                                try {
                                    if (MainActivity.connectServer()) {

                                        uiThread {
                                            toast("Connection succeeded")
                                            dialog.dismiss()
                                        }


                                    } else {
                                        uiThread {
                                            toast("Connection failed")
                                            dialog.dismiss()
                                        }

                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                        }

                    }

                }
            }
        }
    }
}

