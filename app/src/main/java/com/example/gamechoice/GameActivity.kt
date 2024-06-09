package com.example.gamechoice

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.adapter.NoteAdapter
import com.example.gamechoice.databinding.ActivityGameBinding
import com.example.gamechoice.models.GameModel
import com.example.gamechoice.models.NoteModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private var gameid = -1
    private var noteAdapter = NoteAdapter(emptyList())
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        setAdapters()
        setListeners()
    }

    private fun setAdapters() {
        recyclerView = binding.noteRecycler

        noteAdapter = NoteAdapter(mutableListOf())
        recyclerView.adapter = noteAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setListeners() {
        binding.fbAdd.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.note_dialog_layout, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.etNote).text.toString()
            with(builder){
                setTitle("Nueva nota")
                setPositiveButton("Confirmar"){dialog, which ->
                    addNote(editText)
                }
                setNegativeButton("Cancelar"){dialog, which ->
                    Toast.makeText(context, "Not nice", Toast.LENGTH_SHORT).show()
                }
                setView(dialogLayout)
                show()
            }
        }
    }

    @SuppressLint("NewApi")
    private fun addNote(note: String?) {
        lifecycleScope.launch (Dispatchers.IO){
            var lista = mutableListOf<NoteModel>()
            if(!note.isNullOrBlank()){
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val current = LocalDateTime.now().format(formatter)
                var noteModel = NoteModel(note, current.toString())
                lista.add(noteModel)
                noteAdapter.lista = lista
            }
            withContext(Dispatchers.Main){
                noteAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getData() {
        val data = intent.extras
        val gameModel = data?.getSerializable("GAME") as GameModel
        binding.tvName.text = gameModel.name
        Picasso.get().load(gameModel.image).into(binding.ivGame)
        gameid = gameModel.appid
    }

}