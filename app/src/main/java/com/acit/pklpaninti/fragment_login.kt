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
import kotlinx.android.synthetic.main.activity_login.*
import android.util.DisplayMetrics

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_login.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_login : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
        val paddingDp = 4
        val density = context?.getResources()?.getDisplayMetrics()?.density ?: 0.0f
        val paddingPixel = (paddingDp.times(density)).toInt();
        view.setPadding(0,paddingPixel,0,0);

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
                    pass.error = null
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
                    user.error = null
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

        binding.masuk.setOnClickListener{
            if(binding.Edituser.text.toString() == ""){
                binding.user.error = "Nama lengkap wajib diisi"
                validUser = false
            }
            if(binding.Editpass.text.toString() == "") {
                binding.pass.error = "Username wajib diisi"
                validPass = false
            }

            if(validUser == true && validUser == true && validPass == true ){
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_login.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                fragment_login().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}