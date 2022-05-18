package com.aydinpolat.bitcointicker.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.aydinpolat.bitcointicker.R
import com.aydinpolat.bitcointicker.databinding.ActivityMainBinding
import com.aydinpolat.bitcointicker.presentation.fragment.coin_list.CoinListFragment
import com.aydinpolat.bitcointicker.presentation.fragment.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val coinListFragment = CoinListFragment()
        val profileFragment = ProfileFragment()
        setUpBottomNavigation(coinListFragment, profileFragment)
    }

    private fun setUpBottomNavigation(
        coinListFragment: CoinListFragment,
        profileFragment: ProfileFragment
    ) {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.coin_list -> {
                    loadFragment(coinListFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.profile -> {
                    loadFragment(profileFragment)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }

    fun hideOrShowBottomNavigation(isVisible: Boolean) {
        binding.bottomNavigation.isVisible = isVisible
    }
}