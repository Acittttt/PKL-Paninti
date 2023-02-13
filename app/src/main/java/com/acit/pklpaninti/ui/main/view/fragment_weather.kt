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
import com.acit.pklpaninti.data.model.Forecastday
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
                        binding.header.visibility = View.VISIBLE
                        binding.animationLoading.visibility = View.GONE
                        resource.data?.let { forecast -> retrieveList(forecast.forecast.forecastday) }

                        binding.num.text = "${resource.data?.forecast?.forecastday?.component2()?.day?.maxtempC?.toInt()}"
                        binding.temp.text = "/ ${resource.data?.forecast?.forecastday?.component2()?.day?.mintempC?.toInt()}°"
                        binding.weat.text = "${resource.data?.forecast?.forecastday?.component2()?.day?.condition?.text}"
                        binding.speed.text = "${resource.data?.forecast?.forecastday?.component2()?.day?.maxwindKph?.toInt()} km/h"
                        binding.persen.text = "${resource.data?.forecast?.forecastday?.component2()?.day?.avghumidity}%"
                        binding.total.text = "${resource.data?.forecast?.forecastday?.component2()?.day?.dailyChanceOfRain}%"

                        val codeWeather = resource.data?.forecast?.forecastday?.component2()?.day?.condition?.code
                        val animationWeather = binding.weather

                        when (codeWeather) {
                            1000 -> { animationWeather.setAnimation(R.raw.ic_sunny) }
                            1003, 1006 -> { animationWeather.setAnimation(R.raw.ic_partly_cloudy) }
                            1009, 1030 -> { animationWeather.setAnimation(R.raw.ic_mist) }
                            1066, 1069, 1072, 1210, 1213, 1216 -> { animationWeather.setAnimation(R.raw.ic_snow_sunny) }
                            1114, 1117, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264
                            -> { animationWeather.setAnimation(R.raw.ic_snow) }
                            1135, 1147 -> { animationWeather.setAnimation(R.raw.ic_windy) }
                            1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189,
                            1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246,
                            1249, 1252 -> { animationWeather.setAnimation(R.raw.ic_partly_shower) }
                            1273, 1276, 1279, 1282 -> { animationWeather.setAnimation(R.raw.ic_storm_showersday) }
                        }
                        animationWeather.playAnimation()
                    }
                    Status.ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.header.visibility = View.VISIBLE
                        binding.animationLoading.visibility = View.GONE
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.recyclerView.visibility = View.GONE
                        binding.animationLoading.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(forecast: List<Forecastday>) {
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
