package com.atejeda.masterdetail.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.atejeda.masterdetail.databinding.FragmentHomeBinding
import com.atejeda.masterdetail.utils.load


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fab.setOnClickListener {
            binding.img2.load(binding.textUrl.editText?.text.toString(),binding.textName.editText?.text.toString())
        }

        return root
    }

}