package com.acit.pklpaninti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.acit.pklpaninti.databinding.ActivityLoginBinding
import com.acit.pklpaninti.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_login.*

class activity_login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_login)

        val btnReg = findViewById<TextView>(R.id.Regis)
        btnReg.setOnClickListener{
            val intent = Intent (this, activity_register::class.java)
            startActivity(intent)
        }
        binding.Edituser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s?.length ?: 0 >= 1) {
                    user.error = null
                } else {
                    user.error = "Email atau Username wajib diisi"
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


    }

}