package io.sneakspeak.sneakspeak.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.sneakspeak.sneakspeak.data.Message


class ChatAdapter(messageList: List<Message>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.find<TextView>(R.id.loadout_name)


    }

    private val messages = messageList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val menuView = inflater.inflate(R.layout.item_loadout, parent, false)

        // Return a new holder instance
        return ViewHolder(menuView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val message = messages[position]

        viewHolder.name.text = loadout.name

    }

    override fun getItemCount() = messages.size
}
