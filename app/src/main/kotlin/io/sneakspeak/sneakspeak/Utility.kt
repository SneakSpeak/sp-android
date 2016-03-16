package io.sneakspeak.sneakspeak

import android.widget.TextView

fun TextView.containsText() = this.text.toString().replace("\\s+", "").length != 0