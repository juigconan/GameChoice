package com.example.gamechoice.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.databinding.CardLayoutBinding
import com.squareup.picasso.Picasso
import com.example.gamechoice.models.Game

class GameViewHolder(val v: View) : RecyclerView.ViewHolder(v)  {
    private val binding =  CardLayoutBinding.bind(v)
    fun render(game: Game) {
        binding.tvGameName.text = game.name
        binding.tvMainNumber.text = game.main.toString()
        binding.tvCompletionistNumber.text = game.completionist.toString()
        binding.tvSidesNumber.text = game.side.toString()
        binding.tvAllNumber.text = game.all.toString()
        Picasso.get().load("https://steamcdn-a.akamaihd.net/steam/apps/${game.appid}/library_600x900_2x.jpg").into(binding.ivGameImage)
    }
}