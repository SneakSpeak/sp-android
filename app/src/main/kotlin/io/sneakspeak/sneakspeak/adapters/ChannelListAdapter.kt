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
import kotlinx.android.synthetic.main.item_header.view.*
import org.jetbrains.anko.toast
import java.util.*


class ChannelListAdapter(ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        View.OnClickListener {

    val TAG = "ChannelListAdapter"
    val TYPE_HEADER = 0
    val TYPE_ITEM = 1

    val context = ctx

    class ItemViewHolder(channelView: View) : RecyclerView.ViewHolder(channelView) {
        val channelName = channelView.channelName
    }

    class HeaderViewHolder(headerView: View) : RecyclerView.ViewHolder(headerView) {
        val header = headerView.header
    }


    private var joinedChannels = ArrayList<Channel>()
    private var publicChannels = ArrayList<Channel>()

    fun setChannels(public: List<Channel>, joined: List<Channel>) {
        joinedChannels = ArrayList(joined)
        Log.d(TAG, "Joined: $joinedChannels")

        publicChannels = ArrayList(public.filter { !joinedChannels.contains(it) })
        Log.d(TAG, "Public: $publicChannels")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout

        if (viewType == TYPE_HEADER) {
            val headerItem = inflater.inflate(R.layout.item_header, parent, false)
            return ChannelListAdapter.HeaderViewHolder(headerItem)
        } else {
            val channelItem = inflater.inflate(R.layout.item_channel, parent, false)
            channelItem.setOnClickListener(this)
            return ChannelListAdapter.ItemViewHolder(channelItem)
        }


    }

    override fun getItemViewType(position: Int) = when (position) {
        0, joinedChannels.size + 1 -> TYPE_HEADER
        else -> TYPE_ITEM
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ChannelListAdapter.HeaderViewHolder) {
            if (position == 0) viewHolder.header.text = "Joined channels"
            else viewHolder.header.text = "Public channels"
        }

        else if (viewHolder is ChannelListAdapter.ItemViewHolder) {
            if (position <= joinedChannels.size) {
                val item = joinedChannels[position-1]
                viewHolder.channelName.text = item.name
            }

            else {
                val item = publicChannels[position- 2 - joinedChannels.size]
                viewHolder.channelName.text = item.name
            }
        }
    }

    override fun getItemCount() = 2 + publicChannels.size + joinedChannels.size

    override fun onClick(view: View?) {
        val name = view?.channelName?.text?.toString() ?: return

        val channel = publicChannels.find { it.name == name }
                ?: joinedChannels.find { it.name == name}
                ?: return

        Log.d(TAG, "Channel selected: $channel")

        val intent = Intent(context, ChatActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("channel", channel)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

    fun addJoinedChannel(channel: Channel) {
        joinedChannels.add(channel)
        publicChannels.remove(channel)
    }
}