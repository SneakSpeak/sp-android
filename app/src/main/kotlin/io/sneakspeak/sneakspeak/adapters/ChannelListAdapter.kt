package io.sneakspeak.sneakspeak.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.activities.ChatActivity
import io.sneakspeak.sneakspeak.data.Channel
import io.sneakspeak.sneakspeak.data.User
import kotlinx.android.synthetic.main.item_channel.view.*
import org.jetbrains.anko.toast
import java.util.*


class ChannelListAdapter(ctx: Context) : RecyclerView.Adapter<ChannelListAdapter.ViewHolder>(),
        View.OnClickListener {

    val TAG = "UserListAdapter"
    val context = ctx

    class ViewHolder(channelView: View) : RecyclerView.ViewHolder(channelView) {
        val channelName = channelView.channelName
    }

    private var channels: ArrayList<Channel>? = null

    fun setChannels(channelList: List<Channel>) {
        channels = ArrayList(channelList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelListAdapter.ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val channelItem = inflater.inflate(R.layout.item_channel, parent, false)
        channelItem.setOnClickListener(this)

        // Return a new holder instance
        return ChannelListAdapter.ViewHolder(channelItem)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ChannelListAdapter.ViewHolder, position: Int) {
        val channel = channels?.get(position)

        with(viewHolder) {
            channelName.text = channel?.name
        }
    }

    override fun getItemCount() = channels?.size ?: 0

    override fun onClick(view: View?) {
        val name = view?.channelName?.text?.toString() ?: return

        val channel = channels?.find { it.name == name } ?: return

        Log.d(TAG, "Channel selected: $channel")

        val intent = Intent(context, ChatActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("channel", channel)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

    fun addChannel(channel: Channel) {
        if (channels == null)
            channels = ArrayList<Channel>()

        channels?.add(channel)
    }
}