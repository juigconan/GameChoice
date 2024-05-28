package com.example.gamechoice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gamechoice.databinding.ActivityMainBinding
import com.example.gamechoice.fragments.ChatFragment
import com.example.gamechoice.fragments.HomeFragment
import com.example.gamechoice.fragments.NotesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }
    fun setListeners(){
        binding.ibHome.setOnClickListener{
            val fragment = HomeFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(binding.fragmentContainerView.id, fragment, "Fragment home")
            fragmentTransaction.commit()
        }
        binding.ibChat.setOnClickListener{
            val fragment = ChatFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(binding.fragmentContainerView.id, fragment, "Fragment chat")
            fragmentTransaction.commit()
        }
        binding.ibNotes.setOnClickListener{
            val fragment = NotesFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(binding.fragmentContainerView.id, fragment, "Fragment notes")
            fragmentTransaction.commit()
        }

    }
}