package com.example.gamechoice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.R
import com.example.gamechoice.models.Game
import com.example.gamechoice.models.GameModel

class GameAdapter(var lista: List<Game>): RecyclerView.Adapter<GameViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return GameViewHolder(v)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.render(lista[position])
    }
}