package com.atejeda.masterdetail.ui.fragments.location

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.atejeda.masterdetail.databinding.FragmentLocationBinding
import com.atejeda.masterdetail.ui.LocationsListActivity
import com.atejeda.masterdetail.ui.MapsActivity
import com.atejeda.masterdetail.utils.Constants
import com.atejeda.masterdetail.utils.LocationService

class LocationFragment : Fragment() {

    lateinit var binding: FragmentLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )

        binding.start.setOnClickListener {
            startService(Constants.ACTION_START)
        }
        binding.stop.setOnClickListener {
            startService(Constants.ACTION_STOP)
        }
        binding.showMap.setOnClickListener {
            startActivity(Intent(activity, MapsActivity::class.java))
        }
        binding.showListado.setOnClickListener {
            startActivity(Intent(activity, LocationsListActivity::class.java))
        }

        return root
    }

    private fun startService(action:String) {
        var i = Intent(activity, LocationService::class.java)
        i.action = action
        requireActivity().startService(i)
    }

}