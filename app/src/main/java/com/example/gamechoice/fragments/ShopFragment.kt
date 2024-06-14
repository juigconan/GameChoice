package com.example.gamechoice.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.DBConnection
import com.example.gamechoice.GameActivity
import com.example.gamechoice.R
import com.example.gamechoice.adapter.GameAdapter
import com.example.gamechoice.adapter.NoteAdapter
import com.example.gamechoice.databinding.FragmentShopBinding
import com.example.gamechoice.models.GameModel
import com.example.gamechoice.models.NoteModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep
import java.sql.PreparedStatement

class ShopFragment : Fragment(R.layout.fragment_shop) {
    private lateinit var binding: FragmentShopBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    var lista = mutableListOf<GameModel>()
    private var gameAdapter = GameAdapter(emptyList()){viewGame(it)}
    private  var dbConn = DBConnection()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentShopBinding.inflate(layoutInflater)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        setAdapters(view)
        loadFromDB()
        sleep(1000) //Sin esto no funciona
        loadGamesNotes()
        return view
    }
    private fun setAdapters(view: View) {
        recyclerView = view.findViewById(R.id.recyclerGamesNotes)
        gameAdapter = GameAdapter(mutableListOf()){viewGame(it)}
        recyclerView.adapter = gameAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        firestore = Firebase.firestore
        auth = Firebase.auth
    }
    fun viewGame(gameModel: GameModel){
        val i = Intent(context, GameActivity::class.java).apply {
            putExtra("GAME", gameModel)
        }
        startActivity(i)
    }
    private fun loadFromDB(){
        lista.clear()
        firestore.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                var req: PreparedStatement? = dbConn.dbConnect()?.prepareStatement("SELECT * FROM hltb WHERE hltbIndex = ?")
                if (req != null) {
                    for (document in result) {
                        if (document.get("user").toString() == auth.currentUser?.uid.toString()) {
                            var gameid = document.get("game") as Long
                            req.setInt(1, gameid.toInt())
                            var resultSet = req.executeQuery()
                            while(resultSet.next()){
                                var game = GameModel(resultSet.getInt("hltbIndex"),
                                    resultSet.getString("Name"),
                                    resultSet.getDouble("Main"),
                                    resultSet.getDouble("Main + Sides"),
                                    resultSet.getDouble("Completionist"),
                                    resultSet.getDouble("All Styles"),
                                    resultSet.getString("Image"))
                                Log.d("LOL", "${game}")
                                if(!lista.contains(game)){
                                    lista.add(game)
                                }
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("ERROR", "Error getting documents.", exception)
            }
    }
    private fun loadGamesNotes() {
        lifecycleScope.launch (Dispatchers.Unconfined){
            lista.distinct()
            gameAdapter.lista = lista
            withContext(Dispatchers.Main){
               gameAdapter.notifyDataSetChanged()
            }
        }
    }

}