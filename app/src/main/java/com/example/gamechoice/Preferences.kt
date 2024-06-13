package com.example.sharedpreferences071123

import android.content.Context

class Preferences(val c: Context) {
    val storage = c.getSharedPreferences("USERS_PPREFERENCES",0)

    fun setEmail(email: String){
        storage.edit().putString("EMAIL", email).apply()
    }

    fun setVip(vip: Boolean){
        storage.edit().putBoolean("VIP", vip).apply()
    }

    fun getEmail(): String{
        return storage.getString("EMAIL","").toString()
    }

    fun getVip(): Boolean{
        return storage.getBoolean("VIP", false)
    }

    fun borrarTodo(){
        storage.edit().clear().apply()
    }

}