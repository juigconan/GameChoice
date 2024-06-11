package com.example.gamechoice.models

import java.io.Serializable
import com.google.gson.annotations.SerializedName
data class GameModel(
    @SerializedName("response") val response: Response
): Serializable

data class Response(
    @SerializedName("game_count") val gameCount: Int,
    @SerializedName("games") val games: List<Game>
): Serializable
data class Game(
    @SerializedName("appid") val appid: Int,
    @SerializedName("name") val name: String,
    @SerializedName("nain") val main: Double,
    @SerializedName("side") val side: Double,
    @SerializedName("completionist") val completionist: Double,
    @SerializedName("all") val all: Double
): Serializable

