package io.sneakspeak.sneakspeak.fragments.lists

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.adapters.ChannelListAdapter
import io.sneakspeak.sneakspeak.data.Channel
import io.sneakspeak.sneakspeak.data.User
import io.sneakspeak.sneakspeak.managers.DatabaseManager
import io.sneakspeak.sneakspeak.managers.HttpManager
import kotlinx.android.synthetic.main.fragment_channel_list.*
import org.jetbrains.anko.async
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread


class ChannelListFragment : Fragment(), View.OnClickListener {

    val TAG = "ChannelListFragment"

    lateinit var adapter: ChannelListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, bundle: Bundle?)
            = inflater?.inflate(R.layout.fragment_channel_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        updateButton.setOnClickListener(this)
        adapter = ChannelListAdapter(activity)
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        channelList.adapter = adapter
        channelList.layoutManager = manager
    }


    override fun onClick(view: View) {
        if (view == updateButton)
            updateChannels()
//        else if (view == addButton)
//            addChannel()
    }

    private fun updateChannels() {

        val server = DatabaseManager.getCurrentServer()

        if (server == null) {
            toast("Something is wrong.")
            return
        }

        val dialog = indeterminateProgressDialog("Getting channels for server ${server.name}...")
        dialog.show()

        async() {
            val channels = HttpManager.getChannels(server)

            adapter.setChannels(channels)

            uiThread {
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
    }

    private fun addChannel() {

    }
}