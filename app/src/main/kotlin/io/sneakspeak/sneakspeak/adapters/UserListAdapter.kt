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
import io.sneakspeak.sneakspeak.data.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserListAdapter(ctx: Context) : RecyclerView.Adapter<UserListAdapter.ViewHolder>(),
        View.OnClickListener {

    val TAG = "UserListAdapter"
    val context = ctx

    class ViewHolder(userView: View) : RecyclerView.ViewHolder(userView) {
        val name = userView.name
    }

    private var users: List<User>? = null

    fun setUsers(userList: List<User>) {
        users = userList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val userItem = inflater.inflate(R.layout.item_user, parent, false)
        userItem.setOnClickListener(this)

        // Return a new holder instance
        return ViewHolder(userItem)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = users?.get(position)

        with(viewHolder) {
            name.text = user?.name
        }
    }

    override fun getItemCount() = users?.size ?: 0

    override fun onClick(view: View?) {
        val name = view?.name ?: return

        Log.d(TAG, name.text.toString())

        val intent = Intent(context, ChatActivity::class.java)
        val bundle = Bundle()
        bundle.putString("name", name.text.toString())
        intent.putExtras(bundle)
        context.startActivity(intent)
    }
}
