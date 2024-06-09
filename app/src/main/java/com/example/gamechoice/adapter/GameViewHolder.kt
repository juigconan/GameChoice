package com.example.gamechoice.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.databinding.CardLayoutBinding
import com.squareup.picasso.Picasso
import com.example.gamechoice.models.GameModel

class GameViewHolder(val v: View) : RecyclerView.ViewHolder(v)  {
    private val binding =  CardLayoutBinding.bind(v)
    fun render(gameModel: GameModel, onItemSelect: (GameModel) -> Unit) {
        binding.tvGameName.text = gameModel.name
        binding.tvMainNumber.text = gameModel.main.toString()
        binding.tvCompletionistNumber.text = gameModel.completionist.toString()
        binding.tvSidesNumber.text = gameModel.side.toString()
        binding.tvAllNumber.text = gameModel.all.toString()
        Picasso.get().load(gameModel.image).into(binding.ivGameImage)
        itemView.setOnClickListener{
            onItemSelect(gameModel)
        }
    }
}