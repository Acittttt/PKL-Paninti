package com.acit.pklpaninti.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.acit.pklpaninti.R
import com.acit.pklpaninti.databinding.FragmentWeatherBinding
class ActivityHome : AppCompatActivity() {
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.navigate(R.id.fragmentWeatherHome)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentWeatherBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(R.layout.activity_home)
//
//        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentWeatherHome()).commit()
//    }
}