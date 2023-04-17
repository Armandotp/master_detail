package com.atejeda.masterdetail.ui.ui.location

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atejeda.masterdetail.R
import com.atejeda.masterdetail.utils.LocationService

class LocationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_location, container, false)

        startService()

        return view
    }

    fun startService() {

    }

}