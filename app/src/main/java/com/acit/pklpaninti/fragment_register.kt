package com.acit.pklpaninti

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.text.TextUtilsCompat
import com.acit.pklpaninti.databinding.FragmentLoginBinding
import com.acit.pklpaninti.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.*


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
        binding.Editname.addTextChangedListener(object : TextWatcher {
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
        binding.Edituser.addTextChangedListener(object : TextWatcher {
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
        binding.Editemail.addTextChangedListener(object : TextWatcher {
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
        binding.Editpass.addTextChangedListener(object : TextWatcher {
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
        binding.Editconfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == binding.Editpass.text.toString()) {
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
        val intent = Intent(activity, activity_home::class.java)
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
        isNullFullName = if (binding.Editname.length() == 0){
            nullFullName()
            false
        } else {
            true
        }

        return isNullFullName
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

    private fun isNullEmail(): Boolean{
        isNullEmail = if (binding.Editemail.length() == 0){
            nullEmail()
            false
        } else {
            true
        }

        return isNullEmail
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

    private fun isNullConfirmPassword(): Boolean{
        isNullConfirmPassword = if (binding.Editconfirm.length() == 0){
            nullConfirmPassword()
            false
        } else {
            true
        }

        return isNullConfirmPassword
    }


    private fun nullFullName(): Boolean {
        binding.name.error = "Nama lengkap wajib diisi"
        binding.Editname.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullUsername(): Boolean{
        binding.user.error = "Username wajib diisi"
        binding.Edituser.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullEmail(): Boolean{
        binding.email.error = "Email wajib diisi"
        binding.Editemail.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullPassword(): Boolean{
        binding.pass.error = "Password wajib diisi"
        binding.Editpass.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullConfirmPassword(): Boolean{
        binding.confirm.error = "Konfirmasi password wajib diisi"
        binding.Editconfirm.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun regexMinFullname(){
        binding.name.error = "Nama lengkap minimal berisi 2 karakter"
        binding.Editname.setBackgroundResource(R.drawable.bg_textbox_red)
        validName = false
    }

    private fun regexFullName(){
        binding.name.error = "Nama lengkap tidak bisa berisi angka"
        binding.Editname.setBackgroundResource(R.drawable.bg_textbox_red)
        validName = false
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

    private fun regexEmail(target: CharSequence?): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun regexEmailResult(){
        binding.email.error = "Format email tidak sesuai"
        binding.Editemail.setBackgroundResource(R.drawable.bg_textbox_red)
        validEmail = false
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

    private fun regexConfirmPassword(){
        binding.confirm.error = "Konfirmasi password tidak sesuai"
        binding.Editconfirm.setBackgroundResource(R.drawable.bg_textbox_red)
        validConfirmPassword = false
    }

    private fun clearFullName(){
        binding.name.isErrorEnabled = false
        binding.Editname.setBackgroundResource(R.drawable.bg_selector)
        validName = true
    }

    private fun clearUsername(){
        binding.user.isErrorEnabled = false
        binding.Edituser.setBackgroundResource(R.drawable.bg_selector)
        validUsername = true
    }

    private fun clearEmail(){
        binding.email.isErrorEnabled = false
        binding.Editemail.setBackgroundResource(R.drawable.bg_selector)
        validEmail = true
    }

    private fun clearPassword(){
        binding.pass.isErrorEnabled = false
        binding.Editpass.setBackgroundResource(R.drawable.bg_selector)
        validPassword = true
    }

    private fun clearConfirmPassword(){
        binding.confirm.isErrorEnabled = false
        binding.Editconfirm.setBackgroundResource(R.drawable.bg_selector)
        validConfirmPassword=true
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}