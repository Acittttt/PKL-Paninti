package com.acit.pklpaninti.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.acit.pklpaninti.R
import com.acit.pklpaninti.data.api.ApiHelper
import com.acit.pklpaninti.data.api.RetrofitBuilder
import com.acit.pklpaninti.data.model.Forecastday
import com.acit.pklpaninti.ui.main.adapter.WeatherAdapter
import com.acit.pklpaninti.databinding.FragmentWeatherBinding
import com.acit.pklpaninti.ui.base.ViewModelFactory
import com.acit.pklpaninti.ui.main.viewmodel.MainViewModel
import com.acit.pklpaninti.utils.Status
class FragmentWeather : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: MainViewModel
    private var adapter: WeatherAdapter = WeatherAdapter()
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
    private fun backOnClick(){
        binding.back.setOnClickListener{
            val fragment = FragmentWeatherHome()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,fragment)?.commit()
        }
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
        binding.recyclerView.adapter = adapter
    }
    private fun snowLoading(loading: Boolean) {
        binding.apply {
            animationLoading.isVisible = loading
            recyclerView.isVisible =!loading
        }
    }
    private fun setupObservers() {
        viewModel.getForecast().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                snowLoading( resource.status == Status.LOADING)

                when (resource.status) {
                    Status.SUCCESS -> {
                        snowLoading(false)
                        resource.data?.let { forecast -> adapter.items = forecast.forecast.forecastday}
                        resource.data?.forecast?.forecastday?.component2()?.day.let { values ->
                            val Num = "${values?.maxtempC?.toInt()}"
                            val Temp = "/ ${values?.mintempC?.toInt()}°"
                            val Weat = values?.condition?.text
                            val Speed = "${values?.maxwindKph?.toInt()} km/h"
                            val Persen = "${values?.avghumidity}%"
                            val Total = "${values?.dailyChanceOfRain}%"
                            val codeWeather = values?.condition?.code

                            binding.apply {
                                num.text = Num
                                temp.text = Temp
                                weat.text = Weat
                                speed.text = Speed
                                persen.text = Persen
                                total.text = Total
                            }

                            val animationWeather = binding.weather
                            when (codeWeather) {
                                1000 -> { animationWeather.setAnimation(R.raw.ic_sunny) }
                                1003, 1006 -> { animationWeather.setAnimation(R.raw.ic_partly_cloudy) }
                                1009, 1030 -> { animationWeather.setAnimation(R.raw.ic_mist) }
                                1066, 1069, 1072, 1210, 1213, 1216 -> { animationWeather.setAnimation(R.raw.ic_snow_sunny) }
                                1114, 1117, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264 -> { animationWeather.setAnimation(R.raw.ic_snow) }
                                1135, 1147 -> { animationWeather.setAnimation(R.raw.ic_windy) }
                                1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189,
                                1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246,
                                1249, 1252 -> { animationWeather.setAnimation(R.raw.ic_partly_shower) }
                                1273, 1276, 1279, 1282 -> { animationWeather.setAnimation(R.raw.ic_storm_showersday) }
                            }
                            animationWeather.playAnimation()
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {}
                }
            }
        })
    }
}
