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


class fragment_register : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private var isNullName = false
    private var isNullUser = false
    private var isNullEmail = false
    private var isNullPass = false
    private var isNullConfirmPass = false
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root


        val minNameRegex = "^.{2,}$"
        val minUserRegex = "^.{6,}$"
        val UserRegex = "[a-zA-Z0-9._]+"
        val validPassRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$"
        val validNameRegex = "^[a-zA-Z'\\s]*\$"
        var validName = false
        var validUser = false
        var validEmail = false
        var validPass = false
        var validConfirm = false

        val spannable = SpannableStringBuilder(binding.backL.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#55BCE0"))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = fragment_login()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_layout,fragment)?.commit()
            }
        }

        spannable.setSpan(blueColor, 18, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.backL.text = spannable
        binding.backL.movementMethod = LinkMovementMethod.getInstance()

        binding.Editname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)){
                    binding.name.error = "Nama wajib diisi"
                } else if (!(s.toString().matches(minNameRegex.toRegex()))){
                    binding.name.error = "Nama lengkap minimal 2 karakter"
                } else if (!(s.toString().matches(validNameRegex.toRegex()))){
                    binding.name.error = "Nama tidak bisa menggunakan angka dan simbol"
                } else {
                    binding.name.isErrorEnabled = false
                    validName = true
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        binding.Edituser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)){
                    binding.user.error = "Username wajib diisi"
                } else if (!(s.toString().matches(minUserRegex.toRegex()))){
                    binding.user.error = "Username minimal 6 karakter"
                } else if (!(s.toString().matches(UserRegex.toRegex()))){
                    binding.user.error = "Username tidak bisa menggunakan simbol selain . dan _"
                } else {
                    binding.user.isErrorEnabled = false
                    validUser = true
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        binding.Editemail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    binding.email.isErrorEnabled = false
                    validEmail = true
                } else if (!(s?.length ?: 0 >= 1)){
                    binding.email.error = "Email wajib diisi"
                } else {
                    binding.email.error = "Format email tidak sesuai"
                }

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        binding.Editpass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)){
                    binding.pass.error = "Password wajib diisi"
                } else if (!(s.toString().matches(minUserRegex.toRegex()))){
                    binding.pass.error = "Password minimal berisi 6 karakter, 1 huruf kapital dan 1 angka"
                } else if (!(s.toString().matches(validPassRegex.toRegex()))){
                    binding.pass.error = "Password minimal berisi 1 huruf kapital dan 1 angka"
                } else {
                    binding.pass.isErrorEnabled = false
                    validPass = true
                }

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        binding.Editconfirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == binding.Editpass.text.toString()){
                    binding.confirm.isErrorEnabled = false
                    validConfirm = true
                } else if (!(s?.length ?: 0 >= 1)){
                    binding.confirm.error = "Konfirmasi password wajib diisi"
                } else {
                    binding.confirm.error = "Konfirmasi password tidak sesuai"
                }

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        binding.submit.setOnClickListener{
            if(binding.Editname.text.toString() == ""){
                binding.name.error = "Nama lengkap wajib diisi"
                validName = false
            }
            if(binding.Edituser.text.toString() == ""){
                binding.user.error = "Username wajib diisi"
                validUser = false
            }
            if (binding.Editemail.text.toString() == ""){
                binding.email.error = "Email wajib diisi"
                validEmail = false
            }
            if (binding.Editpass.text.toString() == ""){
                binding.pass.error = "Password wajib diisi"
                validPass = false
            }
            if (binding.Editconfirm.text.toString() == ""){
                binding.confirm.error = "Konfirmasi password wajib diisi"
                validConfirm = false
            }

            if(validName == true && validUser == true && validEmail == true && validPass == true && validConfirm == true){
                val intent = Intent (getActivity(), activity_home::class.java)
                getActivity()?.startActivity(intent)
            } else {

                }
            }



        return view
    }

//    private fun signUp(){
//        binding.submit.setOnClickListener{
//            validData()
//        }
//    }
//
//    private fun validData(){
//        nullCheck()
//        validTrue()
//    }
//
//    private fun validTrue(){
//        if (nullName() && nullUser() && nullEmail() && nullPass() && nullConfirmPass())
//            binding()
//    }
//
//    private fun nullCheck(){
//        isNullName()
//        isNullUser()
//        isNullEmail()
//        isNullPass()
//        isNullConfirmPass()
//    }
//
//    private fun isNullName() :Boolean{
//        isNullName = if(binding.Editname.length() == 1){
//            isNullName()
//            false
//        } else {
//            true
//        }
//
//        return isNullName
//    }
//
//    private fun isNullUser(): Boolean {
//        isNullUser= if (binding.Edituser.length() == 1){
//            isNullUser()
//            false
//        } else {
//            true
//        }
//
//        return isNullUser
//    }
//
//    private fun isNullEmail(): Boolean {
//        isNullEmail = if (binding.Editemail.length() == 1){
//            isNullEmail()
//            false
//        } else {
//            true
//        }
//
//        return isNullEmail
//    }
//
//    private fun isNullPass(): Boolean {
//        isNullPass = if (binding.Editpass.length() == 1){
//            isNullPass()
//            false
//        } else{
//            true
//        }
//
//        return isNullPass
//    }
//
//    private fun isNullConfirmPass(): Boolean {
//        isNullConfirmPass = if(binding.Editconfirm.length() == 1){
//            isNullConfirmPass()
//            false
//        } else {
//            true
//        }
//
//        return isNullConfirmPass
//    }
//
//    private fun binding() {
//        val intent = Intent(activity, activity_home::class.java)
//        startActivity(intent)
//    }
//
//    private fun nullName(): Boolean {
//        binding.name.error = "Nama lengkap wajib diisi"
//        return false
//    }
//
//    private fun nullUser(): Boolean {
//        binding.user.error = "Username wajib diisi"
//        return false
//    }
//
//    private fun nullEmail(): Boolean {
//        binding.email.error = "Email wajib diisi"
//        return false
//    }
//
//    private fun nullPass(): Boolean {
//        binding.pass.error = "Password wajib diisi"
//        return false
//    }
//
//    private fun nullConfirmPass(): Boolean {
//        binding.confirm.error = "Konfirmasi Password wajib diisi"
//        return false
//    }
//
//    private fun regexMinName(): Boolean {
//        binding.name.error = "Nama lengkap minimal 2 karakter"
//        return false
//    }
//
//    private fun regexMinUser(): Boolean {
//        binding.user.error = "Username minimal 6 karakter"
//        return false
//    }
//
//    private fun regexEmail(target:CharSequence?): Boolean {
//        return !TextUtils.isEmpty(target)&&Patterns.EMAIL_ADDRESS.matcher(target).matches()
//    }
//
//    private fun regexEmailResult(): Boolean {
//        binding.email.error = "Format email tidak sesuai"
//        return false
//    }
//
//    private fun regexPass(): Boolean {
//        binding.pass.error = "Password minimal berisi 6 karakter, 1 huruf kapital dan 1 angka"
//        return false
//    }
//
//    private fun regexSymbolUser(): Boolean {
//        binding.user.error = "Username tidak bisa menggunakan simbol selain . dan _"
//        return false
//    }
//
//    private fun validatePass(): Boolean {
//        binding.confirm.error = "Konfirmasi Password tidak sesuai"
//        return false
//    }
//
//    private fun clearName(): Boolean {
//        binding.name.isErrorEnabled = false
//        return true
//    }
//
//    private fun clearUser(): Boolean {
//        binding.user.isErrorEnabled = false
//        return true
//    }
//
//    private fun clearEmail(): Boolean {
//        binding.email.isErrorEnabled = false
//        return true
//    }
//
//    private fun clearPass(): Boolean {
//        binding.pass.isErrorEnabled = false
//        return true
//    }
//
//    private fun clearConfirmPass(): Boolean {
//        binding.confirm.isErrorEnabled = false
//        return true
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}