package com.example.gamechoice

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock.sleep
import androidx.appcompat.app.AppCompatActivity
import com.example.gamechoice.databinding.ActivityLoadingBinding
import com.example.gamechoice.databinding.FragmentHomeBinding


class LoadingActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userData = applicationContext.getSharedPreferences("userdata", 0)
        val n = userData.getString("username", "")
        if (userData.getString("username", "") != "") {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        } else {
            val myIntent = Intent(this, LoginActivity::class.java)
            startActivity(myIntent)
        }
    }
}