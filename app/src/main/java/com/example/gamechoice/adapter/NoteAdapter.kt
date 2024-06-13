package com.example.gamechoice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.R
import com.example.gamechoice.models.NoteModel

class NoteAdapter(var lista: List<NoteModel>): RecyclerView.Adapter<NoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return NoteViewHolder(v)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.render(lista[position])
    }

}