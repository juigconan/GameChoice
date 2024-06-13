package com.example.gamechoice.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechoice.DBConnection
import com.example.gamechoice.R
import com.example.gamechoice.adapter.GameAdapter
import com.example.gamechoice.databinding.FragmentHomeBinding
import com.example.gamechoice.databinding.FragmentOptionsBinding

class OptionsFragment : Fragment(R.layout.fragment_options) {
    private lateinit var binding: FragmentOptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOptionsBinding.inflate(layoutInflater)
    }
    private fun setAdapters(view: View) {
        binding.fab.setOnClickListener{

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_options, container, false)
        setAdapters(view)
        return view
    }
}