package com.example.gamechoice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gamechoice.databinding.ActivityMainBinding
import com.example.gamechoice.fragments.ChatFragment
import com.example.gamechoice.fragments.HomeFragment
import com.example.gamechoice.fragments.NotesFragment
import com.example.sharedpreferences071123.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: Preferences
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = Preferences(this)
        setListeners()
        auth = Firebase.auth
        Log.d("userUID",auth.currentUser?.uid.toString())
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
    fun setListeners(){
        binding.ibHome.setOnClickListener{
            val fragment = HomeFragment()
            replaceFragment(fragment)
        }
        binding.ibChat.setOnClickListener{
            val fragment = ChatFragment()
            replaceFragment(fragment)
        }
        binding.ibNotes.setOnClickListener{
            val fragment = NotesFragment()
            replaceFragment(fragment)
        }

    }
}