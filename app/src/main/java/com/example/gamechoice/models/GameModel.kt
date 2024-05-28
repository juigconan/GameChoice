package com.example.gamechoice.models

import java.io.Serializable
import com.google.gson.annotations.SerializedName
data class GameModel(
    @SerializedName("src") val source: String
): Serializable

