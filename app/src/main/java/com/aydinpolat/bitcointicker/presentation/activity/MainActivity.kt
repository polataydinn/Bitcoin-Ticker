package com.aydinpolat.bitcointicker.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.aydinpolat.bitcointicker.CoinService
import com.aydinpolat.bitcointicker.R
import com.aydinpolat.bitcointicker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        collectStateFlow()
    }

    private fun collectStateFlow() {
        lifecycleScope.launchWhenCreated {
            sharedViewModel.state.collect { state ->
                changeNavigationStartDestination(state.appStartDestinationId)
            }
        }
    }

    private fun changeNavigationStartDestination(startDestinationId: Int) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        navHostFragment.findNavController().apply {
            val navGraph = navInflater.inflate(R.navigation.nav_graph)
            navGraph.setStartDestination(startDestinationId)
            graph = navGraph
        }
    }

    override fun onPause() {
        startService(Intent(this, CoinService::class.java))
        super.onPause()
    }
}