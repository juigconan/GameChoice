package com.example.gamechoice.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GameAdapter(var lista: List<FotoModel>): RecyclerView.Adapter<GameViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {

    }

    override fun getItemCount() = itemCount

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}