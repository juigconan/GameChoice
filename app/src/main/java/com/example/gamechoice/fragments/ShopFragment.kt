package com.example.gamechoice.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.gamechoice.R
import com.example.gamechoice.databinding.FragmentShopBinding
import com.example.gamechoice.models.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopFragment : Fragment(R.layout.fragment_shop) {
    private lateinit var binding: FragmentShopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentShopBinding.inflate(layoutInflater)
    }
    private fun setAdapters(view: View) {
        binding.fab.setOnClickListener{

        }
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        setAdapters(view)
        return view
    }
}