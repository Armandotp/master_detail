package com.atejeda.masterdetail.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atejeda.masterdetail.data.model.Pokemon
import com.atejeda.masterdetail.databinding.ItemBinding
import com.atejeda.masterdetail.databinding.ShimmerLastItemBinding
import com.atejeda.masterdetail.ui.interfaces.ElementEvents
import com.atejeda.masterdetail.utils.load

class PokemonAdapter(
    private val elementEvents: ElementEvents, context: Context, var isLoading:Boolean, var last:Int
): ListAdapter<Pokemon, PokemonAdapter.CustomViewHolder>(DiffUtilCallback) {

    var context = context

    private object DiffUtilCallback: DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount(): Int {
        if (super.getItemCount() == 0) {
            return super.getItemCount()
        } else if (isLoading) {
            return super.getItemCount() + 1
        }
        return super.getItemCount()
    }
    override fun getItemViewType(position: Int): Int {
        return if (last != null) {
            if (position == last) 3 else if (position == currentList.size) 2 else 1
        } else {
            if (position == currentList.size) 2 else 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view: View? = null
        if (viewType == 1) {
            val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CustomViewHolder(itemBinding)
        } else if (viewType == 2 || viewType == 3) {
            val itemBinding =
                ShimmerLastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CustomViewHolder(itemBinding)
        }
        return CustomViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if (holder.itemViewType == 3) {
            holder.itemView.layoutParams.height = 0
            holder.itemView.visibility = View.GONE
        }
        if (holder.itemViewType == 1) {
            if (getItem(position) != null) holder.bind(getItem(position),position)
        }
    }

    inner class CustomViewHolder(item: View): RecyclerView.ViewHolder(item) {

        private lateinit var pokemonItemBinding: ItemBinding
        private lateinit var shimmerProgressAnimationBinding: ShimmerLastItemBinding

        constructor(itemBinding: ItemBinding) : this(itemBinding.root) {
            this.pokemonItemBinding = itemBinding
        }

        constructor(itemBinding: ShimmerLastItemBinding) : this(itemBinding.root) {
            this.shimmerProgressAnimationBinding = itemBinding
        }
        @SuppressLint("NewApi")
        fun bind(element: Pokemon, position: Int) = with(pokemonItemBinding){

            pokemonItemBinding.name.text = element.name//element.typesString
            pokemonItemBinding.img.load(element.sprites?.front_default!!,element.name)

            pokemonItemBinding.fav.setOnCheckedChangeListener { _, isChecked ->
                elementEvents.onclickFavourite(element,position,isChecked)
            }
            pokemonItemBinding.fav.isChecked = element.isFavourite

            pokemonItemBinding.cardView.setOnClickListener {
                elementEvents.onclickElement(element,position)
            }
        }
    }

}
