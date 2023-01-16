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
import com.acit.pklpaninti.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class fragment_register : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        val paddingDp = 4
        val density = context?.getResources()?.getDisplayMetrics()?.density ?: 0.0f
        val paddingPixel = (paddingDp.times(density)).toInt();
        view.setPadding(0,paddingPixel,0,0);

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

        binding.Editname.apply{
            onFocusChangeListener =
                View.OnFocusChangeListener { p0, onFocus ->
                    if (onFocus) {
                        setPadding(
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            4.times(density).toInt()
                        )
                    } else {
                        setPadding(
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt()
                        )
                    }
                }
        }

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

        binding.Edituser.apply{
            onFocusChangeListener =
                View.OnFocusChangeListener { p0, onFocus ->
                    if (onFocus) {
                        setPadding(
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            4.times(density).toInt()
                        )
                    } else {
                        setPadding(
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt()
                        )
                    }
                }
        }

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

        binding.Editemail.apply{
            onFocusChangeListener =
                View.OnFocusChangeListener { p0, onFocus ->
                    if (onFocus) {
                        setPadding(
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            4.times(density).toInt()
                        )
                    } else {
                        setPadding(
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt()
                        )
                    }
                }
        }

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

        binding.Editpass.apply{
            onFocusChangeListener =
                View.OnFocusChangeListener { p0, onFocus ->
                    if (onFocus) {
                        setPadding(
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            4.times(density).toInt()
                        )
                    } else {
                        setPadding(
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt()
                        )
                    }
                }
        }

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

        binding.Editconfirm.apply{
            onFocusChangeListener =
                View.OnFocusChangeListener { p0, onFocus ->
                    if (onFocus) {
                        setPadding(
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            4.times(density).toInt()
                        )
                    } else {
                        setPadding(
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt(),
                            16.times(density).toInt()
                        )
                    }
                }
        }

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}