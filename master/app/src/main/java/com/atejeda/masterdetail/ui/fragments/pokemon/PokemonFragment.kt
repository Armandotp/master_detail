package com.atejeda.masterdetail.ui.fragments.pokemon

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atejeda.masterdetail.data.model.Pokemon
import com.atejeda.masterdetail.databinding.FragmentPokemonBinding
import com.atejeda.masterdetail.ui.DetailActivity
import com.atejeda.masterdetail.ui.adapters.PokemonAdapter
import com.atejeda.masterdetail.ui.interfaces.ElementEvents
import com.atejeda.masterdetail.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokemonFragment : Fragment(), ElementEvents {

    lateinit var binding: FragmentPokemonBinding
    lateinit var adapter: PokemonAdapter
    var isApiProgress = false
    lateinit var viewModel: PokemonViewModel
    var pokemonCount = 0
    var pokemonList: MutableList<Pokemon> = mutableListOf()
    var BROADCAST_DEFAULT: String = "fav"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this)[PokemonViewModel::class.java]

        setAdapter()
        initObservers()

        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(broadCastReceiver, IntentFilter(BROADCAST_DEFAULT))

        return root
    }

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            try {
                var position = intent?.getIntExtra("element",0)
                var isChecked = intent?.getBooleanExtra("isChecked",false)
                pokemonList[position!!].isFavourite = isChecked!!
                adapter.notifyItemChanged(position)
                viewModel.setFavourite(pokemonList[position].id,isChecked)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initObservers(){
        viewModel.allPokemons.observe(viewLifecycleOwner) {
            pokemonCount += it.size
            if(pokemonList.isNullOrEmpty()){
                pokemonList = it as MutableList<Pokemon>
                adapter?.submitList(pokemonList)
            }else{
                pokemonList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.isApiProgress.observe(viewLifecycleOwner){
            isApiProgress = it
            if(it && pokemonList.isNullOrEmpty()){
                binding?.shimmerLayout?.startShimmer()
                binding?.shimmerLayout?.visibility = View.VISIBLE
            }else{
                binding?.shimmerLayout?.stopShimmer()
                binding?.shimmerLayout?.visibility = View.GONE

            }
        }
    }

    private fun setAdapter(){
        adapter = PokemonAdapter(this, requireActivity(),true,898)
        val llm = LinearLayoutManager(requireActivity())
        llm.orientation = LinearLayoutManager.VERTICAL

        binding.recyclerView.layoutManager = llm

        binding.recyclerView.adapter = adapter

        binding.recyclerView.hasFixedSize()
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!isApiProgress && pokemonCount == pokemonList.size) {
                    //viewModel.getAll((pokemonCount+1),pokemonList.size)
                    viewModel.getAll(Constants.LIMIT,pokemonList.size)
                    pokemonCount = pokemonList.size
                }
            }
        })
        viewModel.getAll(Constants.LIMIT,0)
    }

    override fun onclickFavourite(element: Pokemon, postion: Int, checked: Boolean) {
        try {
            viewModel.setFavourite(element.id,checked)
            pokemonList[postion].isFavourite = checked
            adapter.notifyItemChanged(postion)
        }catch (i:IllegalStateException){
            i.printStackTrace()
        }
    }

    override fun onclickElement(element: Pokemon, postion: Int) {
        startActivity(Intent(activity, DetailActivity::class.java)
            .putExtra("pokemon",element)
            .putExtra("position",postion))
    }

}