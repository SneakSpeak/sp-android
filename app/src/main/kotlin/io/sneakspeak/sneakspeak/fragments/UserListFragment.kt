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
import io.sneakspeak.sneakspeak.managers.HttpManager
import io.sneakspeak.sneakspeak.managers.SettingsManager
import kotlinx.android.synthetic.main.fragment_user_list.*
import org.jetbrains.anko.async
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.progressDialog
import org.jetbrains.anko.uiThread
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

    override fun onClick(view: View) {
        val server = "http://${SettingsManager.getAddress()}:${SettingsManager.getPort()}"
        val dialog = indeterminateProgressDialog("Getting users for server ${SettingsManager.getAddress()}...")
        dialog.show()

        async() {
            val users = HttpManager.getUsers(server)
            updateUsers(users)
            uiThread {
                dialog.dismiss()
            }
        }
    }

    fun updateUsers(userList: List<String>) {
        val users = userList.map { name -> User(name) }
        adapter.setUsers(users)
        onUiThread { adapter.notifyDataSetChanged() }
    }
}