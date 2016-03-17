package io.sneakspeak.sneakspeak.data

import java.io.Serializable


data class Server(val address: String,
                  val port: String,
                  val name: String,
                  val token: String,
                  val username: String) : Serializable