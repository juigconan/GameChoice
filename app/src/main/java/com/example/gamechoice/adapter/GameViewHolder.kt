package com.example.gamechoice.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.databinding.CardLayoutBinding
import com.example.gamechoice.models.GameModel
import com.squareup.picasso.Picasso

class GameViewHolder(val v: View) : RecyclerView.ViewHolder(v)  {
    private val binding =  CardLayoutBinding.bind(v)
    fun render(game: GameModel, onItemSelect: (GameModel) -> Unit) {
        binding.tvGameName.text = game.name
        binding.tvMainNumber.text = game.main.toString()
        binding.tvCompletionistNumber.text = game.completionist.toString()
        binding.tvSidesNumber.text = game.side.toString()
        binding.tvAllNumber.text = game.all.toString()
        Picasso.get().load(game.image).into(binding.ivGameImage)
        itemView.setOnClickListener{
            onItemSelect(game)
        }
    }
}