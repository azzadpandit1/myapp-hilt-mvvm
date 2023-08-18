package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //bottom navigation setup
        setupBottomNavigation()





    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.certificateFragment,
                R.id.profileFragment,
                R.id.homeFragment,
                R.id.coursesFragment,
                R.id.moreFragment
            )
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment
                || destination.id == R.id.moreFragment
                || destination.id == R.id.certificateFragment
                || destination.id == R.id.coursesFragment
                || destination.id == R.id.profileFragment
            ) {
                binding.toolbar.visibility = View.VISIBLE
                binding.bottomNav.visibility = View.VISIBLE
            } else {
//                binding.toolbar.visibility = View.GONE
                binding.bottomNav.visibility = View.GONE

            }
        }
    }
}