package com.acit.pklpaninti.ui.main.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import com.acit.pklpaninti.data.model.Hour
import com.acit.pklpaninti.ui.main.adapter.WeatherHomeAdapter
import com.acit.pklpaninti.databinding.FragmentWeatherHomeBinding
import com.acit.pklpaninti.ui.base.ViewModelFactory
import com.acit.pklpaninti.ui.main.viewmodel.MainViewModel
import com.acit.pklpaninti.utils.Status
import java.text.DateFormat
import java.text.SimpleDateFormat
import kotlin.time.Duration.Companion.days


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
        viewModel.getForecast()
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
                        visibilitySuccessError()
                        resource.data?.let { forecast -> retrieveList(forecast.forecast.forecastday.component1().hour) }

                        binding.textView.text = resource.data?.location?.name
                        binding.num.text = "${resource.data?.current?.tempC?.toInt()}°"
                        binding.weat.text = resource.data?.current?.condition?.text
                        binding.speed.text = "${resource.data?.current?.windKph?.toInt()} km/h"
                        binding.persen.text = "${resource.data?.current?.humidity}%"
                        binding.total.text = "${resource.data?.forecast?.forecastday?.component1()?.day?.dailyChanceOfRain}%"
                        val date = resource.data?.forecast?.forecastday?.component1()?.date
                        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val dateFormat: DateFormat = SimpleDateFormat("EEEE, dd MMMM")
                        val newDate: String = dateFormat.format(df.parse(date))

                        binding.date.text = newDate

                        val time = resource.data?.current?.lastUpdated?.substring(11, 16)
                        val tf: DateFormat = SimpleDateFormat("HH:mm")
                        val timeFormat: DateFormat = SimpleDateFormat("a")
                        val timeMarker: String = timeFormat.format(tf.parse(time))
                        val codeWeather = resource.data?.current?.condition?.code
                        val animationWeather = binding.weather

                        if (timeMarker == "AM") {
                            when (codeWeather) {
                                1000 -> {
                                    animationWeather.setAnimation(R.raw.ic_sunny)
                                }
                                1003, 1006 -> {
                                    animationWeather.setAnimation(R.raw.ic_partly_cloudy)
                                }
                                1009, 1030 -> {
                                    animationWeather.setAnimation(R.raw.ic_mist)
                                }
                                1066, 1069, 1072, 1210, 1213, 1216 -> {
                                    animationWeather.setAnimation(R.raw.ic_snow_sunny)
                                }
                                1114, 1117, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264
                                -> {
                                    animationWeather.setAnimation(R.raw.ic_snow)
                                }
                                1135, 1147 -> {
                                    animationWeather.setAnimation(R.raw.ic_windy)
                                }
                                1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189,
                                1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246,
                                1249, 1252 -> {
                                    animationWeather.setAnimation(R.raw.ic_partly_shower)
                                }
                                1273, 1276, 1279, 1282 -> {
                                    animationWeather.setAnimation(R.raw.ic_storm_showersday)
                                }
                            }
                            animationWeather.playAnimation()
                        } else if (timeMarker == "PM") {
                            when (codeWeather) {
                                1000 -> {
                                    animationWeather.setAnimation(R.raw.ic_night)
                                }
                                1003, 1006 -> {
                                    animationWeather.setAnimation(R.raw.ic_cloudy_night)
                                }
                                1009, 1030 -> {
                                    animationWeather.setAnimation(R.raw.ic_mist)
                                }
                                1066, 1069, 1072, 1210, 1213, 1216 -> {
                                    animationWeather.setAnimation(R.raw.ic_snow_night)
                                }
                                1114, 1117, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264
                                -> {
                                    animationWeather.setAnimation(R.raw.ic_snow)
                                }
                                1135, 1147 -> {
                                    animationWeather.setAnimation(R.raw.ic_windy)
                                }
                                1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189,
                                1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246,
                                1249, 1252 -> {
                                    animationWeather.setAnimation(R.raw.ic_rainy_night)
                                }
                                1273, 1276, 1279, 1282 -> {
                                    animationWeather.setAnimation(R.raw.ic_storm)
                                }
                            }
                            animationWeather.playAnimation()
                        }
                    }

                    Status.ERROR -> {
                        visibilitySuccessError()
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        visibilityLoading()
                    }
                }
            }
        })
    }

    private fun retrieveList(forecast: List<Hour>) {
        adapter.apply {
            this.items = forecast
        }
    }

    private fun visibilitySuccessError() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.day.visibility = View.VISIBLE
        binding.dys.visibility = View.VISIBLE
        binding.header.visibility = View.VISIBLE
        binding.animationLoading.visibility = View.GONE
}

    private fun visibilityLoading(){
        binding.recyclerView.visibility = View.GONE
}

    private fun nextSevenDay(){
        binding.dys.setOnClickListener{
            val fragment = fragment_weather()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,fragment)?.commit()
        }
    }
}
