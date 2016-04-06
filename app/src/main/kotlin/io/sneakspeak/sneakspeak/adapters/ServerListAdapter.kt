package io.sneakspeak.sneakspeak.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.data.Server
import kotlinx.android.synthetic.main.item_server.view.*


class ServerListAdapter : RecyclerView.Adapter<ServerListAdapter.ViewHolder>() {

    class ViewHolder(serverView: View) : RecyclerView.ViewHolder(serverView) {
        val name = serverView.serverName
        val port = serverView.serverPort
        val address = serverView.serverAddress
        val userName = serverView.userName
    }

    private var servers: List<Server>? = null

    fun setServers(serverList: List<Server>) {
        servers = serverList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val messageView = inflater.inflate(R.layout.item_server, parent, false)

        // Return a new holder instance
        return ViewHolder(messageView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val server = servers?.get(position)

        with(viewHolder) {
            name.text = server?.name
            port.text = server?.port
            address.text = server?.address
            userName.text = server?.username
        }
    }

    override fun getItemCount() = servers?.size ?: 0
}
