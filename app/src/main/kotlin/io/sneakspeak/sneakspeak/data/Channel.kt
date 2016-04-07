package io.sneakspeak.sneakspeak.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Channel(val name: String, val public: Boolean, val id: Int): Serializable