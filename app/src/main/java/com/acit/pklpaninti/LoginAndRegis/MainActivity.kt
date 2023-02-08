package com.acit.pklpaninti.LoginAndRegis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acit.pklpaninti.R
import com.acit.pklpaninti.databinding.FragmentLoginBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment_login()).commit()
    }

}
