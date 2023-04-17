package com.atejeda.masterdetail.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.atejeda.masterdetail.R
import com.atejeda.masterdetail.databinding.ActivityMasterBinding
import com.atejeda.masterdetail.ui.fragments.home.HomeFragment
import com.atejeda.masterdetail.ui.fragments.location.LocationFragment
import com.atejeda.masterdetail.ui.fragments.pokemon.PokemonFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MasterActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMasterBinding

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.appBarMaster.toolbar
        setSupportActionBar(toolbar)


        drawerLayout = binding.drawerLayout
        navView = binding.navView
        navView.itemIconTintList = null;
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment){
        try {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment_content_master, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_master)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav1 -> {
                replaceFragment(HomeFragment())
            }
            R.id.nav2 -> {
                replaceFragment(PokemonFragment())
            }
            R.id.nav3 -> {
                replaceFragment(LocationFragment())
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        this.finish()
    }
}