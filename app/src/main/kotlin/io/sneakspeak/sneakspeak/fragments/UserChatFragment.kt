package io.sneakspeak.sneakspeak.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView


class UserChatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
        = ChatUI<UserChatFragment>().createView(AnkoContext.create(activity, this))


    private class ChatUI<T> : AnkoComponent<T> {
        override fun createView(ui: AnkoContext<T>) = with(ui) {
            verticalLayout {
                padding = 16
                recyclerView {
                    adapter = null

                }.lparams { width=matchParent; height=matchParent }

                linearLayout {
                    editText {
                        inputType = InputType.TYPE_CLASS_TEXT
                    }.lparams { width = matchParent }

                    button {
                        text = "Lähetä"
                        onClick {
                            toast("Viestin lähetys")
                        }
                    }
                }
            }
        }


    }
}
