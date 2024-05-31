package com.example.gamechoice.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.models.GameModel
import com.squareup.picasso.Picasso

class GameViewHolder(val v: View) : RecyclerView.ViewHolder(v)  {
    fun render(gameModel: GameModel) {
      //  Picasso.get().load(imagen.source.foto).into(binding.ivCiudad)
       // binding.tvFotografo.text = imagen.fotografo
    }
}