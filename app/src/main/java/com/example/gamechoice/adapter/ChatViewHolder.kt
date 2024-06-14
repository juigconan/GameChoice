package com.example.mensajes041223.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.R
import com.example.gamechoice.databinding.MensajesLayoutBinding
import com.example.gamechoice.models.MensajeModel
import java.text.SimpleDateFormat
import java.util.Date


class ChatViewHolder(v : View): RecyclerView.ViewHolder(v) {
    private val binding = MensajesLayoutBinding.bind(v)
    fun render(mensaje: MensajeModel, user: String){
        binding.tvMensaje.text = mensaje.mensaje
        binding.tvFecha.text = fecha(mensaje.fecha)
        if (user == mensaje.user){
            binding.consLayout.setBackgroundColor(binding.consLayout.context.getColor(R.color.gold))
        }else{
            binding.consLayout.setBackgroundColor(binding.consLayout.context.getColor(R.color.medium_green))
        }
    }

    private  fun fecha(fecha: Long): String{
        val date = Date(fecha)
        val format = SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
        return  format.format(date)
    }

}
