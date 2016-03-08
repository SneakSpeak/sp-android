package io.sneakspeak.sneakspeak

import android.os.Bundle
import android.os.Handler
import android.support.v4.os.ResultReceiver
import android.util.Log


class UserResultReceiver(handler: Handler) : ResultReceiver(handler) {

    val TAG = "UserResultReceiver"
    var receiver: Receiver? = null

    interface Receiver {
        fun onReceiveResult(resultCode: Int, resultData: Bundle?);
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        super.onReceiveResult(resultCode, resultData)

        receiver?.onReceiveResult(resultCode, resultData)
    }
}