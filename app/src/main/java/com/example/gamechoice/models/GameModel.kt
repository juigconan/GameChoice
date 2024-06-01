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
    @SerializedName("playtime_forever") val playtime: Int
): Serializable

