package com.atejeda.masterdetail.ui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.atejeda.masterdetail.databinding.FragmentHomeBinding
import com.atejeda.masterdetail.utils.load


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //binding.img.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")

        binding.fab.setOnClickListener {
            binding.img.load(binding.textUrl.editText?.text.toString(),binding.textName.editText?.text.toString())
        }

        return root
    }

}