package com.atejeda.masterdetail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atejeda.masterdetail.data.model.LocationItem
import com.atejeda.masterdetail.databinding.ActivityLocationsListBinding
import com.atejeda.masterdetail.ui.adapters.AdapterLocations
import com.atejeda.masterdetail.utils.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.firebase.firestore.FirebaseFirestore

class LocationsListActivity : AppCompatActivity() {

    lateinit var binding:ActivityLocationsListBinding
    lateinit var adapter: AdapterLocations
    private val db = FirebaseFirestore.getInstance()
    var points: MutableList<LocationItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "List"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setAdapter()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setAdapter(){
        if(points.isNullOrEmpty()){
            binding?.shimmerLayout?.startShimmer()
            binding?.shimmerLayout?.visibility = View.VISIBLE
        }else{
            binding?.shimmerLayout?.stopShimmer()
            binding?.shimmerLayout?.visibility = View.GONE

        }
        adapter = AdapterLocations(this)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = llm
        binding.recyclerView.adapter = adapter
        binding.recyclerView.hasFixedSize()
        getAll()
    }

    private fun getAll(){
        db.collection(Constants.DB_FIRESTORE).get().addOnSuccessListener {
            points = it.map { point->
                LocationItem(point["lat"].toString().toDouble(),
                    point["long"].toString().toDouble(),
                    point["date"].toString())
            } as MutableList<LocationItem>
            adapter.submitList(points)
            binding?.shimmerLayout?.stopShimmer()
            binding?.shimmerLayout?.visibility = View.GONE
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }
}