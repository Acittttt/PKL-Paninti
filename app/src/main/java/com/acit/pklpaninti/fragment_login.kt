package com.acit.pklpaninti

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acit.pklpaninti.databinding.FragmentLoginBinding
import android.util.DisplayMetrics
import com.acit.pklpaninti.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_login.*


class fragment_login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var isNullUsername = false
    private var isNullPassword = false

    val usernameRegex = "[a-zA-Z0-9._]+"
    val passwordRegex ="^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$"
    var validUsername = false
    var validPassword = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view()
    }

    private fun view(){
        val spannable = SpannableStringBuilder(binding.Regis.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#4496B3"))

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = fragment_register()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_layout, fragment)?.commit()
            }
        }
        spannable.setSpan(blueColor, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.Regis.text = spannable
        binding.Regis.movementMethod = LinkMovementMethod.getInstance()

        username()
        password()
        submit()
    }

    private fun username(){
        binding.Edituser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)) {
                    nullUsername()
                } else {
                    clearUsername()
                }
            }
        })
    }

    private fun password(){
        binding.Editpass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)) {
                    nullPassword()
                } else {
                    clearPassword()
                }
            }
        })
    }

    private fun submit(){
        binding.submit.setOnClickListener{
            validation()
        }
    }

    private fun validation(){
        nullCheck()
        validationTrue()
    }

    private fun validationTrue(){
        if(isNullUsername() && isNullPassword() && validUsername==true && validPassword==true)
            binding()
    }

    private fun binding(){
        val intent = Intent(activity, activity_home::class.java)
        startActivity(intent)
    }

    private fun nullCheck(){
        isNullUsername()
        isNullPassword()
    }

    private fun isNullUsername(): Boolean{
        isNullUsername = if (binding.Edituser.length() == 0){
            nullUsername()
            false
        } else {
            true
        }

        return isNullUsername
    }

    private fun isNullPassword(): Boolean{
        isNullPassword = if (binding.Editpass.length() == 0){
            nullPassword()
            false
        } else {
            true
        }

        return isNullPassword
    }

    private fun nullUsername(): Boolean{
        binding.user.error = "Username wajib diisi"
        binding.Edituser.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullPassword(): Boolean{
        binding.pass.error = "Password wajib diisi"
        binding.Editpass.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun regexMinUsername(){
        binding.user.error = "Username minimal berisi 6 karakter"
        binding.Edituser.setBackgroundResource(R.drawable.bg_textbox_red)
        validUsername = false
    }

    private fun regexUsername(){
        binding.user.error = "Username tidak bisa menggunakan simbol selain . dan _"
        binding.Edituser.setBackgroundResource(R.drawable.bg_textbox_red)
        validUsername = false
    }

    private fun regexMinPassword(){
        binding.pass.error = "Password minimal berisi 6 karakter, 1 huruf kapital dan 1 angka"
        binding.Editpass.setBackgroundResource(R.drawable.bg_textbox_red)
        validPassword=false
    }
    private fun regexPassword(){
        binding.pass.error = "Password minimal berisi 1 huruf kapital dan 1 angka"
        binding.Editpass.setBackgroundResource(R.drawable.bg_textbox_red)
        validPassword = false
    }

    private fun clearUsername(){
        binding.user.isErrorEnabled = false
        binding.Edituser.setBackgroundResource(R.drawable.bg_selector)
        validUsername = true
    }

    private fun clearPassword(){
        binding.pass.isErrorEnabled = false
        binding.Editpass.setBackgroundResource(R.drawable.bg_selector)
        validPassword = true
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        val view = binding.root
//
//        var validUser = false
//        var validPass = false
//
//
//        val spannable = SpannableStringBuilder(binding.Regis.text.toString())
//        val blueColor = ForegroundColorSpan(Color.parseColor("#55BCE0"))
//        val clickableSpan = object : ClickableSpan() {
//            override fun onClick(widget: View) {
//                val fragment = fragment_register()
//                val transaction = fragmentManager?.beginTransaction()
//                transaction?.replace(R.id.frame_layout,fragment)?.commit()
//            }
//        }
//        spannable.setSpan(blueColor, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannable.setSpan(clickableSpan, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        binding.Regis.text = spannable
//        binding.Regis.movementMethod = LinkMovementMethod.getInstance()
//
//        binding.Editpass.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                if(s?.length ?: 0 >= 1) {
//                    pass.isErrorEnabled = false
//                    validPass = true
//                } else {
//                    pass.error = "Password wajib diisi"
//                }
//            }
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//        })
//
//        binding.Edituser.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                if(s?.length ?: 0 >= 1) {
//                    user.isErrorEnabled = false
//                    validUser = true
//                } else {
//                    user.error = "Email atau Username wajib diisi"
//                }
//            }
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//        })
//
//        binding.submit.setOnClickListener{
//            if(binding.Edituser.text.toString() == ""){
//                binding.user.error = "Email atau Username wajib diisi"
//                validUser = false
//            }
//            if(binding.Editpass.text.toString() == "") {
//                binding.pass.error = "Password wajib diisi"
//                validPass = false
//            }
//
//            if(validUser == true && validPass == true ){
//                val intent = Intent (getActivity(), activity_home::class.java)
//                getActivity()?.startActivity(intent)
//            } else {
//
//            }
//        }
//        return view
//
//
//    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}