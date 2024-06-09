package com.example.gamechoice.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.databinding.NoteLayoutBinding
import com.example.gamechoice.models.NoteModel

class NoteViewHolder(val v: View) : RecyclerView.ViewHolder(v)  {
    private val binding =  NoteLayoutBinding.bind(v)
    fun render(note: NoteModel) {
        binding.tvFecha.text = note.date
        binding.tvNota.text = note.text
    }
}