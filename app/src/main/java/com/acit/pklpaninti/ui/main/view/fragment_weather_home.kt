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
import com.acit.pklpaninti.ui.main.adapter.WeatherHomeAdapter
import com.acit.pklpaninti.databinding.FragmentWeatherHomeBinding
import com.acit.pklpaninti.ui.base.ViewModelFactory
import com.acit.pklpaninti.ui.main.viewmodel.MainViewModel
import com.acit.pklpaninti.utils.Status


class fragment_weather_home : Fragment() {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter : WeatherHomeAdapter
    private lateinit var binding : FragmentWeatherHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherHomeBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCardView()
        setupViewModel()
        setupUI()
        setupObservers()
        nextSevenDay()
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
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        adapter = WeatherHomeAdapter()
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

    private fun nextSevenDay(){
        binding.dys.setOnClickListener{
            val fragment = fragment_weather()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,fragment)?.commit()
        }
    }
}

//    private lateinit var binding: FragmentWeatherHomeBinding
//    private val WeatherHomeAdapter by lazy { WeatherHomeAdapter() }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentWeatherHomeBinding.inflate(layoutInflater)
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
//        nextPage()
//    }
//
//    private fun recyclerView(){
//        WeatherHomeAdapter.differ.submitList(loadData())
//
//        binding.apply {
//            binding.recyclerView.apply {
//                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
//                adapter = WeatherHomeAdapter
//            }
//        }
//    }
//
//    private fun loadData(): MutableList<WeatherHome>{
//        val data : MutableList<WeatherHome> = mutableListOf()
//
//        data.add(
//            WeatherHome(
//            R.drawable.icon_cloud,
//            getString(R.string.persen),
//            getString(R.string.time2)
//        )
//        )
//        data.add(
//            WeatherHome(
//            R.drawable.icon_dry,
//            getString(R.string.persen),
//            getString(R.string.time)
//        )
//        )
//        data.add(
//            WeatherHome(
//            R.drawable.icon_strom,
//            getString(R.string.persen),
//            getString(R.string.time3)
//        )
//        )
//        data.add(
//            WeatherHome(
//            R.drawable.icon_rainy,
//            getString(R.string.persen),
//            getString(R.string.time4)
//        )
//        )
//
//        return data
//    }
//
//    private fun cardView() {
//        binding.header.setBackgroundResource(R.drawable.bg_weather)
//    }
//
//    private fun nextPage(){
//        binding.dys.setOnClickListener {
//            next()
//        }
//    }
//
//    private fun next(){
//        val fragment = fragment_weather()
//        val transaction = fragmentManager?.beginTransaction()
//        transaction?.replace(R.id.frame_layout, fragment)?.commit()
//    }
