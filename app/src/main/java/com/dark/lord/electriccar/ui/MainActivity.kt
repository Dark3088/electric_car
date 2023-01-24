package com.dark.lord.electriccar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.dark.lord.electriccar.R
import com.dark.lord.electriccar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpListeners()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
       setupWithNavController(binding.bottomNavigation, navController)

    }

    private fun setUpListeners() {
        binding.fabCalculate.setOnClickListener {
            startActivity(Intent(this, CalculateAutonomy::class.java))
        }
    }
}

