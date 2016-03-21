package io.sneakspeak.sneakspeak.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.data.Message
import kotlinx.android.synthetic.main.item_message.view.*
import java.util.*


class ChatAdapter(messageList: List<Message> = ArrayList<Message>()) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message = itemView.message
        val sender = itemView.sender
        val time = itemView.time
    }

    private val messages = ArrayList<Message>(messageList)

    fun addMessage(msg: Message) {
        messages.add(msg)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val messageView = inflater.inflate(R.layout.item_message, parent, false)

        // Return a new holder instance
        return ViewHolder(messageView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val msg = messages[position]

        with(viewHolder) {
            message.text = msg.message
            sender.text = msg.sender
            time.text = msg.time
        }
    }

    override fun getItemCount() = messages.size
}
