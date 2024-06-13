package com.example.gamechoice.providers

import com.example.gamechoice.models.GameModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GameInterface {
    @GET("IPlayerService/GetOwnedGames/v0001/")
    suspend fun getOwnedGames(@Query("key") key: String, @Query("steamid") steamid: String, @Query("format") format: String = "json"): GameModel
}
