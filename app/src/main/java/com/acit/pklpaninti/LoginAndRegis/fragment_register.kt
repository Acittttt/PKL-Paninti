package com.acit.pklpaninti.LoginAndRegis

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acit.pklpaninti.R
import com.acit.pklpaninti.databinding.FragmentRegisterBinding
import com.acit.pklpaninti.ui.main.view.Activity_home


class fragment_register : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var isNullFullName = false
    private var isNullUsername = false
    private var isNullEmail = false
    private var isNullPassword = false
    private var isNullConfirmPassword = false

    val minTwoCharRegex = "^.{2,}$"
    val minSixCharRegex = "^.{6,}$"
    val passwordRegex ="^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$"
    val fullNameRegex = "[A-Za-z '-]+"
    val usernameRegex = "[a-zA-Z0-9._]+"
    var validName = false
    var validUsername = false
    var validEmail = false
    var validPassword = false
    var validConfirmPassword = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view()
    }

    private fun view(){
        val spannable = SpannableStringBuilder(binding.backL.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#4496B3"))

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = fragment_login()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_layout, fragment)?.commit()
            }
        }
        spannable.setSpan(blueColor, 18, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.backL.text = spannable
        binding.backL.movementMethod = LinkMovementMethod.getInstance()

        fullName()
        username()
        email()
        password()
        confirmPassword()
        submit()
    }

    private fun fullName(){
        binding.editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)) {
                    nullFullName()
                } else if (!(s.toString().matches(fullNameRegex.toRegex()))) {
                    regexFullName()
                } else if (!(s.toString().matches(minTwoCharRegex.toRegex()))) {
                    regexMinFullname()
                } else {
                    clearFullName()
                }
            }
        })
    }

    private fun username(){
        binding.editUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)) {
                    nullUsername()
                } else if (!(s.toString().matches(usernameRegex.toRegex()))) {
                    regexUsername()
                } else if (!(s.toString().matches(minSixCharRegex.toRegex()))) {
                    regexMinUsername()
                } else {
                    clearUsername()
                }
            }
        })
    }

    private fun email(){
        binding.editEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (regexEmail(s)) {
                    clearEmail()
                } else if (!(s?.length ?: 0 >= 1)) {
                    nullEmail()
                } else {
                    regexEmailResult()
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
                } else if (!(s.toString().matches(minSixCharRegex.toRegex()))) {
                    regexMinPassword()
                } else if (!(s.toString().matches(passwordRegex.toRegex()))) {
                    regexPassword()
                } else {
                    clearPassword()
                }
            }
        })
    }

    private fun confirmPassword(){
        binding.editConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == binding.editPass.text.toString()) {
                    clearConfirmPassword()
                } else if (!(s?.length ?: 0 >= 1)) {
                    nullConfirmPassword()
                } else {
                    regexConfirmPassword()
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
        if(isNullFullName() && isNullUsername() && isNullEmail() && isNullPassword() && isNullConfirmPassword()
            && validName==true && validUsername==true && validEmail==true && validPassword==true && validConfirmPassword==true)
            binding()
    }

    private fun binding(){
        val intent = Intent(activity, Activity_home::class.java)
        startActivity(intent)
    }

    private fun nullCheck(){
        isNullFullName()
        isNullUsername()
        isNullEmail()
        isNullPassword()
        isNullConfirmPassword()
    }

    private fun isNullFullName(): Boolean{
        isNullFullName = if (binding.editName.length() == 0){
            nullFullName()
            false
        } else {
            true
        }

        return isNullFullName
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

    private fun isNullEmail(): Boolean{
        isNullEmail = if (binding.editEmail.length() == 0){
            nullEmail()
            false
        } else {
            true
        }

        return isNullEmail
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

    private fun isNullConfirmPassword(): Boolean{
        isNullConfirmPassword = if (binding.editConfirm.length() == 0){
            nullConfirmPassword()
            false
        } else {
            true
        }

        return isNullConfirmPassword
    }


    private fun nullFullName(): Boolean {
        binding.name.error = getString(R.string.valName)
        binding.editName.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullUsername(): Boolean{
        binding.user.error = getString(R.string.valUser)
        binding.editUser.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullEmail(): Boolean{
        binding.email.error = getString(R.string.valEmail)
        binding.editEmail.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullPassword(): Boolean{
        binding.pass.error = getString(R.string.valPass)
        binding.editPass.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullConfirmPassword(): Boolean{
        binding.confirm.error = getString(R.string.valConfirm)
        binding.editConfirm.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun regexMinFullname(){
        binding.name.error = getString(R.string.regexFullName)
        binding.editName.setBackgroundResource(R.drawable.bg_textbox_red)
        validName = false
    }

    private fun regexFullName(){
        binding.name.error = getString(R.string.regexName)
        binding.editName.setBackgroundResource(R.drawable.bg_textbox_red)
        validName = false
    }

    private fun regexMinUsername(){
        binding.user.error = getString(R.string.regexMinUser)
        binding.editUser.setBackgroundResource(R.drawable.bg_textbox_red)
        validUsername = false
    }

    private fun regexUsername(){
        binding.user.error = getString(R.string.regexUser)
        binding.editUser.setBackgroundResource(R.drawable.bg_textbox_red)
        validUsername = false
    }

    private fun regexEmail(target: CharSequence?): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun regexEmailResult(){
        binding.email.error = getString(R.string.regexEmail)
        binding.editEmail.setBackgroundResource(R.drawable.bg_textbox_red)
        validEmail = false
    }

    private fun regexMinPassword(){
        binding.pass.error = getString(R.string.regexMinPass)
        binding.editPass.setBackgroundResource(R.drawable.bg_textbox_red)
        validPassword=false
        }
    private fun regexPassword(){
        binding.pass.error = getString(R.string.regexPass)
        binding.editPass.setBackgroundResource(R.drawable.bg_textbox_red)
        validPassword = false
    }

    private fun regexConfirmPassword(){
        binding.confirm.error = getString(R.string.regexConfirm)
        binding.editConfirm.setBackgroundResource(R.drawable.bg_textbox_red)
        validConfirmPassword = false
    }

    private fun clearFullName(){
        binding.name.isErrorEnabled = false
        binding.editName.setBackgroundResource(R.drawable.bg_selector)
        validName = true
    }

    private fun clearUsername(){
        binding.user.isErrorEnabled = false
        binding.editUser.setBackgroundResource(R.drawable.bg_selector)
        validUsername = true
    }

    private fun clearEmail(){
        binding.email.isErrorEnabled = false
        binding.editEmail.setBackgroundResource(R.drawable.bg_selector)
        validEmail = true
    }

    private fun clearPassword(){
        binding.pass.isErrorEnabled = false
        binding.editPass.setBackgroundResource(R.drawable.bg_selector)
        validPassword = true
    }

    private fun clearConfirmPassword(){
        binding.confirm.isErrorEnabled = false
        binding.editConfirm.setBackgroundResource(R.drawable.bg_selector)
        validConfirmPassword=true
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}