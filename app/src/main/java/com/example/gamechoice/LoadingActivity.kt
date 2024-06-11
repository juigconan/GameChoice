package com.example.gamechoice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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