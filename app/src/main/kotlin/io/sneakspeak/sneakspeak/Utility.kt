package io.sneakspeak.sneakspeak

import android.util.Log
import android.widget.TextView

fun log(msg: String) = Log.d("SneakSpeak", msg)

fun TextView.containsText() = this.text.toString().replace("\\s+", "").length != 0