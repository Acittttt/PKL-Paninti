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

//    private lateinit var binding: FragmentWeatherBinding
//    private val WeatherAdapter by lazy { WeatherAdapter() }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentWeatherBinding.inflate(layoutInflater)
//        val view = binding.root
//
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view()
//    }
//
//    private fun view(){
//        cardView()
//        recyclerView()
//        backPage()
//    }
//
//    private fun recyclerView(){
//        WeatherAdapter.differ.submitList(loadData())
//
//        binding.apply {
//            binding.recyclerView.apply {
//                layoutManager = LinearLayoutManager(this.context)
//                adapter = WeatherAdapter
//            }
//        }
//    }
//
//    private fun loadData(): MutableList<Weather>{
//        val data : MutableList<Weather> = mutableListOf()
//
//        data.add(
//            Weather(
//            R.drawable.icon_cloud,
//            getString(R.string.day1),
//            getString(R.string.weather),
//            getString(R.string.temp1)
//        )
//        )
//        data.add(
//            Weather(
//            R.drawable.icon_cloud,
//            getString(R.string.day2),
//            getString(R.string.weather),
//            getString(R.string.temp2)
//        )
//        )
//        data.add(
//            Weather(
//            R.drawable.icon_rainy,
//            getString(R.string.day3),
//            getString(R.string.wheat2),
//            getString(R.string.temp3)
//        )
//        )
//        data.add(
//            Weather(
//            R.drawable.icon_strom,
//            getString(R.string.day4),
//            getString(R.string.wheat3),
//            getString(R.string.temp4)
//        )
//        )
//        data.add(
//            Weather(
//            R.drawable.icon_sunny,
//            getString(R.string.day5),
//            getString(R.string.wheat4),
//            getString(R.string.temp5)
//        )
//        )
//        data.add(
//            Weather(
//            R.drawable.icon_sunny,
//            getString(R.string.day6),
//            getString(R.string.wheat4),
//            getString(R.string.temp6)
//        )
//        )
//        data.add(
//            Weather(
//            R.drawable.icon_sunny,
//            getString(R.string.day7),
//            getString(R.string.wheat4),
//            getString(R.string.temp7)
//        )
//        )
//
//        return data
//    }
//
//    private fun backPage(){
//        binding.back.setOnClickListener {
//            back()
//        }
//    }
//
//    private fun back(){
//        val fragment = fragment_weather_home()
//        val transaction = fragmentManager?.beginTransaction()
//        transaction?.replace(R.id.frame_layout, fragment)?.commit()
//    }
//
//    private fun cardView() {
//        binding.header.setBackgroundResource(R.drawable.bg_weather)
//    }
//
//
//
