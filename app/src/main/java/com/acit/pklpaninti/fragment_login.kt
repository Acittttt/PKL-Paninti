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
import kotlinx.android.synthetic.main.fragment_login.*


class fragment_login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        var validUser = false
        var validPass = false


        val spannable = SpannableStringBuilder(binding.Regis.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#55BCE0"))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = fragment_register()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_layout,fragment)?.commit()
            }
        }
        spannable.setSpan(blueColor, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.Regis.text = spannable
        binding.Regis.movementMethod = LinkMovementMethod.getInstance()

        binding.Editpass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s?.length ?: 0 >= 1) {
                    pass.isErrorEnabled = false
                    validPass = true
                } else {
                    pass.error = "Password wajib diisi"
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.Edituser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s?.length ?: 0 >= 1) {
                    user.isErrorEnabled = false
                    validUser = true
                } else {
                    user.error = "Email atau Username wajib diisi"
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.submit.setOnClickListener{
            if(binding.Edituser.text.toString() == ""){
                binding.user.error = "Email atau Username wajib diisi"
                validUser = false
            }
            if(binding.Editpass.text.toString() == "") {
                binding.pass.error = "Password wajib diisi"
                validPass = false
            }

            if(validUser == true && validPass == true ){
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