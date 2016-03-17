package io.sneakspeak.sneakspeak.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sneakspeak.sneakspeak.R
import io.sneakspeak.sneakspeak.activities.RegisterActivity
import io.sneakspeak.sneakspeak.data.Server
import kotlinx.android.synthetic.main.fragment_servers.*

class ServerFragment : Fragment(), View.OnClickListener {
    val TAG = "ServerFragment"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, bundle: Bundle?)
            = inflater?.inflate(R.layout.fragment_servers, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        addServer.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        val intent = Intent(activity, RegisterActivity::class.java)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "Received result with code $resultCode, data $data")

        // If the registration was successful we have received server data
        // Todo: save it to the storage and push to the list
        if (resultCode == Activity.RESULT_OK) {
            val server = data?.extras?.getSerializable("server") as Server
            Log.d(TAG, server.toString())
        }
    }
}