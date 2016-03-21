package io.sneakspeak.sneakspeak.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.data.User
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*


class ServerListAdapter : RecyclerView.Adapter<ServerListAdapter.ViewHolder>() {

    class ViewHolder(serverView: View) : RecyclerView.ViewHolder(serverView) {
        val name = serverView.name
    }

    private var users = ArrayList<User>()

    fun addUsers(userList: ArrayList<User>) {
        users = userList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val messageView = inflater.inflate(R.layout.item_user, parent, false)

        // Return a new holder instance
        return ViewHolder(messageView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = users[position]

        with(viewHolder) {
            name.text = user.name
        }
    }

    override fun getItemCount() = users.size
}
