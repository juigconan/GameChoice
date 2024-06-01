package com.example.gamechoice.providers

import com.example.gamechoice.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // No puedo usar R.string.api_root.toString() para baseUrl, ya que da error asi que va hardcodeado
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.steampowered.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiClient = retrofit.create(GameInterface::class.java)
}