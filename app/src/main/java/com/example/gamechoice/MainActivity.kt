package com.example.gamechoice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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