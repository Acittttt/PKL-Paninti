package com.acit.pklpaninti.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.acit.pklpaninti.R
import com.acit.pklpaninti.data.api.ApiHelper
import com.acit.pklpaninti.data.api.RetrofitBuilder
import com.acit.pklpaninti.data.model.WeatherData
import com.acit.pklpaninti.ui.main.adapter.WeatherAdapter
import com.acit.pklpaninti.databinding.FragmentWeatherBinding
import com.acit.pklpaninti.ui.base.ViewModelFactory
import com.acit.pklpaninti.ui.main.viewmodel.MainViewModel
import com.acit.pklpaninti.utils.Status

class fragment_weather : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var adapter: WeatherAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCardView()
        setupViewModel()
        setupUI()
        setupObservers()
        backOnClick()
    }

    private fun setCardView(){
        binding.header.setBackgroundResource(R.drawable.bg_weather)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[MainViewModel::class.java]
    }

    private fun setupUI(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = WeatherAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getForecast().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        resource.data?.let { forecast -> retrieveList(forecast.list) }
                    }
                    Status.ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(forecast: List<WeatherData>) {
        adapter.apply {
            this.items = forecast
        }
    }
    private fun backOnClick(){
        binding.back.setOnClickListener{
            val fragment = fragment_weather_home()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,fragment)?.commit()
        }
    }
}
