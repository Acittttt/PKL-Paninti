package com.acit.pklpaninti

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.acit.pklpaninti.databinding.ActivityLoginBinding
import com.acit.pklpaninti.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*


class activity_register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_register)

        val btnback = findViewById<TextView>(R.id.backL)
        btnback.setOnClickListener{
            val intent = Intent (this, activity_login::class.java)
            startActivity(intent)
        }
        binding.Editnama.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateName(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }

    private fun validateName(name: String) {
        val pattern = "^[a-zA-Z]+\$".toRegex()
        val pattern2 = "^.{2,}$".toRegex()
        if (!pattern.matches(name)) {
            nama.error = "Nama lengkap wajib diisi"
        } else if (!pattern2.matches(name)) {
            nama.error = "Nama lengkap minimal 2 karakter"
        }
    }

}