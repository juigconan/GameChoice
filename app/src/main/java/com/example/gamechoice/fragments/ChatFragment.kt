package com.example.gamechoice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.R
import com.example.gamechoice.databinding.FragmentChatBinding
import com.example.gamechoice.models.MensajeModel
import com.example.mensajes041223.adapter.ChatAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ChatFragment : Fragment(R.layout.fragment_chat) {
    private lateinit var binding: FragmentChatBinding
    private var user = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var dataBase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private var listaMensajes = mutableListOf<MensajeModel>()
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentChatBinding.inflate(layoutInflater)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        auth = Firebase.auth
        user = auth.currentUser?.uid.toString()
        initDb()
        setRecycler(view)
        setDbListener(view)
        setListener(view)
        return view
    }

    private fun setListener(view: View) {
        view.findViewById<ImageView>(R.id.ivSend).setOnClickListener{
            var contenido = view.findViewById<EditText>(R.id.etChat).text.toString().trim()
            if(contenido.isNotEmpty()){
                val fecha = System.currentTimeMillis()
                val mensaje = MensajeModel(contenido, user, fecha)
                reference.child(fecha.toString()).setValue(mensaje)
                    .addOnSuccessListener {
                        view.findViewById<EditText>(R.id.etChat).setText("")
                    }
                    .addOnFailureListener{

                    }
            }
        }
    }

    private fun setDbListener(view:View) {
        val dbListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaMensajes.clear()
                for (data in snapshot.children){
                    val mensaje = data.getValue(MensajeModel::class.java)
                    if (mensaje != null){
                        listaMensajes.add(mensaje)
                    }else{
                        continue
                    }
                }
                listaMensajes.sortBy { it.fecha }
                adapter.lista = listaMensajes
                adapter.notifyDataSetChanged()
                view.findViewById<RecyclerView>(R.id.recyclerChat).scrollToPosition(listaMensajes.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        reference.addValueEventListener(dbListener)
    }

    private fun setRecycler(view: View) {
        val linearLayoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.recyclerChat).layoutManager = linearLayoutManager
        adapter = ChatAdapter(listaMensajes,user)
        view.findViewById<RecyclerView>(R.id.recyclerChat).adapter = adapter
    }

    private fun initDb() {
        dataBase = FirebaseDatabase.getInstance("https://gamechoice-14073-default-rtdb.firebaseio.com/")
        reference = dataBase.getReference("mensajesChat")
    }
}