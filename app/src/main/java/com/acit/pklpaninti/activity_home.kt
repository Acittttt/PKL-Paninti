package com.acit.pklpaninti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acit.pklpaninti.databinding.FragmentLoginBinding
import com.acit.pklpaninti.databinding.FragmentWeatherBinding

class activity_home : AppCompatActivity() {
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment_weather()).commit()
    }
}