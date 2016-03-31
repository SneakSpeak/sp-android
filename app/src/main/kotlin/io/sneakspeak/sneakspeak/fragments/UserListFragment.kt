package io.sneakspeak.sneakspeak.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.adapters.UserListAdapter
import io.sneakspeak.sneakspeak.data.User
import kotlinx.android.synthetic.main.fragment_user_list.*
import java.util.*

class UserListFragment : Fragment(), View.OnClickListener {

    val TAG = "UserListFragment"

    lateinit var adapter: UserListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, bundle: Bundle?)
            = inflater?.inflate(R.layout.fragment_user_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        updateButton.setOnClickListener(this)
        adapter = UserListAdapter(activity)
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        userList.adapter = adapter
        userList.layoutManager = manager
    }

    override fun onClick(view: View?) {
        Log.d(TAG, "clicki")
    }

    fun updateUsers(userList: ArrayList<String>) {
        val users = userList.map { name -> User(name) }
        adapter.addUsers(users)
    }
}