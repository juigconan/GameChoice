package com.example.gamechoice.fragments

import android.content.Intent
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
import com.example.gamechoice.DBConnection
import com.example.gamechoice.GameActivity
import com.example.gamechoice.R
import com.example.gamechoice.adapter.GameAdapter
import com.example.gamechoice.databinding.FragmentHomeBinding
import com.example.gamechoice.models.GameModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.PreparedStatement


class HomeFragment : Fragment(R.layout.fragment_home){
    private lateinit var binding: FragmentHomeBinding
    private var gameAdapter = GameAdapter(emptyList()){viewGame(it)}
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private  var dbConn = DBConnection()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
    }

    private fun setAdapters(view: View) {
        recyclerView = view.findViewById(R.id.recyclerGames)
        searchView = view.findViewById(R.id.searchView)

        gameAdapter = GameAdapter(mutableListOf()){viewGame(it)}
        recyclerView.adapter = gameAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setAdapters(view)
        setListeners()
        //loadGames()
        return view
    }

    private fun setListeners() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadGames(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    fun viewGame(gameModel: GameModel){
        val i = Intent(context, GameActivity::class.java).apply {
            putExtra("GAME", gameModel)
        }
        startActivity(i)
    }

    private fun loadGames(game: String?) {
        lifecycleScope.launch (Dispatchers.IO){
                var lista = mutableListOf<GameModel>()
                val req: PreparedStatement? = dbConn.dbConnect()?.prepareStatement("SELECT * FROM gamechoice WHERE Name like ?")
                if (req != null) {
                    req.setString(1, "%$game%")
                    var resultSet = req.executeQuery()
                    while(resultSet.next()){
                        //TODO: Poner bien la imagen y el indice cuando la BD tenga los datos
                        var gameModel = GameModel(resultSet.getInt("steamIndex"),
                            resultSet.getString("Name"),
                            resultSet.getDouble("Main"),
                            resultSet.getDouble("Main + Sides"),
                            resultSet.getDouble("Completionist"),
                            resultSet.getDouble("All Styles"),
                            //resultSet.getString("Image")
                            "https://howlongtobeat.com/games/68151_Elden_Ring.jpg?width=250"
                        )
                        lista.add(gameModel)
                    }
                    gameAdapter.lista = lista
                    req.close()
                }else{
                   Log.e("ERROR", "Result de la DB es null")
                }
            withContext(Dispatchers.Main){
                gameAdapter.notifyDataSetChanged()
            }
        }
    }

    /*
    private fun loadGames() {
        lifecycleScope.launch (Dispatchers.IO){
            var results = mutableListOf<GameModel>()
            try {
                results.clear()
                results.addAll(apiClient.getOwnedGames("key","steamid").response.games)
            }catch (e: Exception){
                Log.e("ERROR", "No se ha podido acceder con la API: " + e.message.toString())
            }
            gameAdapter.lista = results
            if(results.isNotEmpty()){
                gameAdapter.lista = results
            }else{
                gameAdapter.lista = emptyList()
                Log.w("WARNING","Lista de API vacia")
            }
            withContext(Dispatchers.Main){
                gameAdapter.notifyDataSetChanged()

            }
        }
    }
    */
}