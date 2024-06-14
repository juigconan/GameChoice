package com.example.gamechoice.models

class MensajeModel {
    constructor()
    constructor(msg: String, user:String, fecha: Long){
        this.mensaje = msg
        this.user = user
        this.fecha = fecha
    }


    var mensaje = ""
    var fecha = 0L
    var user = ""
}