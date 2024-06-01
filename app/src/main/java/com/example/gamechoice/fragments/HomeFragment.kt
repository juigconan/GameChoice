package com.example.gamechoice.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.R
import com.example.gamechoice.adapter.GameAdapter
import com.example.gamechoice.databinding.FragmentHomeBinding
import com.example.gamechoice.models.Game
import com.example.gamechoice.providers.ApiClient.apiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private var gameAdapter = GameAdapter(emptyList())
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
    }/*

    private fun setAdapters(view: View) {
        val layoutManager = LinearLayoutManager(context)
        recycler = view.findViewById(R.id.recyclerGames)
        recycler.layoutManager = layoutManager
        binding.recyclerGames.adapter = gameAdapter
    }*/

    private fun setAdapters(view: View) {
        recyclerView = view.findViewById(R.id.recyclerGames)
        searchView = view.findViewById(R.id.searchView)

        gameAdapter = GameAdapter(mutableListOf())
        recyclerView.adapter = gameAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setAdapters(view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadGames(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        loadGames()
        return view
    }
    private fun loadGames(game: String?) {
        lifecycleScope.launch (Dispatchers.IO){
            var game = Game(game!!.toInt(), 63)
            var lista = gameAdapter.lista.toMutableList()
            lista.add(game)
            gameAdapter.lista = lista
            withContext(Dispatchers.Main){
                gameAdapter.notifyDataSetChanged()
            }
        }
    }
    private fun loadGames() {
        lifecycleScope.launch (Dispatchers.IO){
            var results = mutableListOf<Game>()
            try {
                results.clear()
                //TODO: Extraer claves de keyvalt de azure
                results.addAll(apiClient.getOwnedGames("key","steamid").response.games)
            }catch (e: Exception){
                //TODO: Esto es totalmente temporal, extraer los juegos del usuario de la base de datos
                results.clear()
                val game = Game(50,65)
                results.add(game)
            }
            gameAdapter.lista = results
            if(results.isNotEmpty()){
                gameAdapter.lista = results
            }else{
                gameAdapter.lista = emptyList()
                Log.e("Esta vacio","Vacio")
            }
            withContext(Dispatchers.Main){
                gameAdapter.notifyDataSetChanged()

            }
        }
    }

}