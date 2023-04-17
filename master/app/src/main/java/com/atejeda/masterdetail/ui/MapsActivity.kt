package com.atejeda.masterdetail.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.atejeda.masterdetail.R
import com.atejeda.masterdetail.databinding.ActivityMapsBinding
import com.atejeda.masterdetail.utils.Constants

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.firestore.FirebaseFirestore

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Map"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getAll()
    }

    private fun getAll(){
        db.collection(Constants.DB_FIRESTORE).get().addOnSuccessListener {
            var points = it.map { point->
                LatLng(point["lat"].toString().toDouble(), point["long"].toString().toDouble())
            }
            val polyline = mMap.addPolyline(PolylineOptions().addAll(points))

            val pattern = listOf(
                Dot(), Gap(10F), Dash(50F), Gap(10F)
            )
            polyline.pattern = pattern
            mMap.moveCamera(CameraUpdateFactory.newLatLng(points[0]))
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(points[0], 17f)
            )
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}