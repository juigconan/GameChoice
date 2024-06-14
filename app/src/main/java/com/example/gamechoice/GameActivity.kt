package com.example.gamechoice

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.adapter.NoteAdapter
import com.example.gamechoice.databinding.ActivityGameBinding
import com.example.gamechoice.models.GameModel
import com.example.gamechoice.models.NoteModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private var gameid = -1
    private var noteAdapter = NoteAdapter(emptyList()){deleteNote(it)}

    private lateinit var recyclerView: RecyclerView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    var lista = mutableListOf<NoteModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        setAdapters()
        setListeners()
        loadNotes()
    }

    private fun deleteNote(note: NoteModel) {
        AlertDialog.Builder(this)
            .setTitle("Borrar")
            .setMessage("¿Estas seguro de que quieres borrar esta nota?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Confirmar") { dialog, whichButton ->
                firestore.collection("notes")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            if(document.get("text").toString() == note.text){
                                firestore.collection("notes").document(document.id).delete()
                                    .addOnSuccessListener {
                                        lista.remove(note)
                                        Log.d("Success", "DocumentSnapshot successfully deleted!")
                                        loadNotes()
                                    }
                                    .addOnFailureListener { e -> Log.w("ERROR", "Error deleting document", e) }
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w("ERROR", "Error getting documents.", exception)
                    }
            }
            .setNegativeButton("Cancelar", null).show()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        System.exit(0)
    }

    private fun loadNotes() {
        lifecycleScope.launch (Dispatchers.IO){
        firestore.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                lista.clear()
                for (document in result) {
                    if(document.get("user").toString() == auth.currentUser?.uid.toString()
                        && document.get("game").toString() == gameid.toString()){
                        val note = NoteModel(document.get("text").toString(), document.get("date").toString())
                        lista.add(note)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("ERROR", "Error getting documents.", exception)
            }
            noteAdapter.lista = lista
            withContext(Dispatchers.Main){
                waitForDB()
            }
        }
    }


    private fun setAdapters() {
        recyclerView = binding.noteRecycler
        firestore = Firebase.firestore
        noteAdapter = NoteAdapter(mutableListOf()) { deleteNote(it) }
        auth = Firebase.auth
        recyclerView.adapter = noteAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setListeners() {
        binding.fbAdd.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.note_dialog_layout, null)

            with(builder){
                setTitle("Nueva nota")
                setPositiveButton("Confirmar"){dialog, which ->
                    val editText = dialogLayout.findViewById<EditText>(R.id.etNote).text.toString()
                    addNote(editText)
                }
                setNegativeButton("Cancelar"){dialog, which ->
                }
                setView(dialogLayout)
                show()
            }
        }
    }

    @SuppressLint("NewApi")
    private fun addNote(note: String?) {
        lifecycleScope.launch (Dispatchers.IO){
            if(!note.isNullOrBlank()){
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val current = LocalDateTime.now().format(formatter)
                var noteModel = NoteModel(note, current.toString())
                lista.add(noteModel)
                noteAdapter.lista = lista
                saveToFB(noteModel)
            }
            withContext(Dispatchers.Main){
                noteAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun waitForDB(){
        sleep(2000)
        noteAdapter.notifyDataSetChanged()
    }

    private fun saveToFB(note: NoteModel) {
        val noteHash = hashMapOf(
            "game" to gameid,
            "user" to auth.currentUser?.uid.toString(),
            "text" to note.text,
            "date" to note.date
        )
        firestore.collection("notes")
            .add(noteHash)
            .addOnSuccessListener { documentReference ->
                Log.d("Success", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Fail", "Error adding document", e)
            }
    }

    private fun getData() {
        val data = intent.extras
        val game = data?.getSerializable("GAME") as GameModel
        binding.tvName.text = game.name
        binding.tvMainNumber.text = game.main.toString()
        binding.tvCompletionistNumber.text = game.completionist.toString()
        binding.tvSidesNumber.text = game.side.toString()
        binding.tvAllNumber.text = game.all.toString()
        Picasso.get().load(game.image).into(binding.ivGame)
        gameid = game.appid
    }

}