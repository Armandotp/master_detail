package com.atejeda.masterdetail.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atejeda.masterdetail.data.model.LocationItem
import com.atejeda.masterdetail.databinding.ItemLocationListBinding

class AdapterLocations(context: Context)
    : ListAdapter<LocationItem, AdapterLocations.CustomViewHolder>(DiffUtilCallback) {

    var context = context

    private object DiffUtilCallback: DiffUtil.ItemCallback<LocationItem>() {
        override fun areItemsTheSame(oldItem: LocationItem, newItem:LocationItem): Boolean {
            return true
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemBinding = ItemLocationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }

    inner class CustomViewHolder(item: View): RecyclerView.ViewHolder(item) {

        private lateinit var binding: ItemLocationListBinding


        constructor(itemBinding: ItemLocationListBinding) : this(itemBinding.root) {
            this.binding = itemBinding
        }

        @SuppressLint("NewApi")
        fun bind(element: LocationItem, position: Int) = with(binding){

            binding.datetime.text = element.dateTime
            binding.lat.text = element.lat.toString()
            binding.lon.text = element.long.toString()

        }
    }

}
