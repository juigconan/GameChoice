package com.example.gamechoice.models

import java.io.Serializable
import com.google.gson.annotations.SerializedName
data class GameModel(
    val appid: Int,
    val name: String,
    val main: Double,
    val side: Double,
    val completionist: Double,
    val all: Double,
    val image: String
): Serializable

