package com.example.gamechoice

import android.os.StrictMode
import android.util.Log
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBConnection{

    fun dbConnect(): Connection? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var conn: Connection? = null
        val connString: String
        var password = ""
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connString = "jdbc:jtds:sqlserver://sqlservergamechoice.database.windows.net:1433;DatabaseName=sqldbgamechoice;user=juigconan@sqlservergamechoice;password={$password};Protocol=TLSv1.2;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=60;ssl=request"
            conn = DriverManager.getConnection(connString)
        }catch(ex : SQLException){
            Log.e("sqlEx", ex.message.toString())
        }catch(ex1 : ClassNotFoundException){
            Log.e("classNotFound", ex1.message.toString())
        }catch(ex2 : Exception){
            Log.e("generalEx", ex2.message.toString())
        }
        return conn
    }
}