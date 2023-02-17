package com.acit.pklpaninti.login

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
import com.acit.pklpaninti.R
import com.acit.pklpaninti.databinding.FragmentLoginBinding
import com.acit.pklpaninti.ui.main.view.ActivityHome
class FragmentLogin : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var isNullUsername = false
    private var isNullPassword = false

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
        val spannable = SpannableStringBuilder(binding.regis.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#4496B3"))

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = FragmentRegister()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_layout, fragment)?.commit()
            }
        }
        spannable.setSpan(blueColor, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.regis.text = spannable
        binding.regis.movementMethod = LinkMovementMethod.getInstance()

        username()
        password()
        submit()
    }
    private fun username(){
        binding.editUser.addTextChangedListener(object : TextWatcher {
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
        binding.editPass.addTextChangedListener(object : TextWatcher {
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
        val intent = Intent(activity, ActivityHome::class.java)
        startActivity(intent)
    }
    private fun nullCheck(){
        isNullUsername()
        isNullPassword()
    }
    private fun isNullUsername(): Boolean{
        isNullUsername = if (binding.editUser.length() == 0){
            nullUsername()
            false
        } else {
            true
        }

        return isNullUsername
    }
    private fun isNullPassword(): Boolean{
        isNullPassword = if (binding.editPass.length() == 0){
            nullPassword()
            false
        } else {
            true
        }

        return isNullPassword
    }
    private fun nullUsername(): Boolean{
        binding.user.error = getString(R.string.valUser)
        binding.editUser.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }
    private fun nullPassword(): Boolean{
        binding.pass.error = getString(R.string.valPass)
        binding.editPass.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }
    private fun clearUsername(){
        binding.user.isErrorEnabled = false
        binding.editUser.setBackgroundResource(R.drawable.bg_selector)
        validUsername = true
    }
    private fun clearPassword(){
        binding.pass.isErrorEnabled = false
        binding.editPass.setBackgroundResource(R.drawable.bg_selector)
        validPassword = true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}