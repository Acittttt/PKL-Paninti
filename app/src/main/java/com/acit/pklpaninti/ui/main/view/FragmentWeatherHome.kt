package com.acit.pklpaninti.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.acit.pklpaninti.R
import com.acit.pklpaninti.data.api.ApiHelper
import com.acit.pklpaninti.data.api.RetrofitBuilder
import com.acit.pklpaninti.ui.main.adapter.WeatherHomeAdapter
import com.acit.pklpaninti.databinding.FragmentWeatherHomeBinding
import com.acit.pklpaninti.ui.base.ViewModelFactory
import com.acit.pklpaninti.ui.main.viewmodel.MainViewModel
import com.acit.pklpaninti.utils.Status
import com.acit.pklpaninti.utils.UnitPreference
import java.text.DateFormat
import java.text.SimpleDateFormat

class FragmentWeatherHome : Fragment() {

    private lateinit var viewModel : MainViewModel
    private lateinit var binding : FragmentWeatherHomeBinding
    private var adapter : WeatherHomeAdapter = WeatherHomeAdapter()

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
        binding.recyclerView.adapter = adapter
    }

    private fun nextSevenDay(){
        binding.dys.setOnClickListener{
            val unitPreferenceHome = viewModel.unitPreference.value.toString()
            val action = FragmentWeatherHomeDirections.actionFragmentWeatherHomeToFragmentWeather(unitPreferenceHome)
            findNavController().navigate(action)
        }
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
                snowLoading(resource.status == Status.LOADING)
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { forecast -> adapter.items = forecast.forecast.forecastday.component1().hour }
                        resource.data?.current.let { values ->
                            val temp = "${values?.tempC?.toInt()}°"
                            val condition = values?.condition?.text
                            val windSpeed = "${values?.windKph?.toInt()} km/h"
                            val humidityPercent = "${values?.humidity}%"
                            val codeWeather = values?.condition?.code
                            val time = values?.lastUpdated?.substring(11, 13)?.toInt()
                            val animationWeather = binding.weather
                            var timeCondition = ""

                            binding.apply {
                                speed.text = windSpeed
                                persen.text = humidityPercent
                                num.text = temp
                                weat.text = condition
                            }

                            when (time) {
                                in 6..18 -> timeCondition = "Day"
                                in 19..23, in 0..5 -> timeCondition = "Night"
                            }

                            if (timeCondition == "Day") {
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
                            } else if (timeCondition == "Night") {
                                when (codeWeather) {
                                    1000 -> { animationWeather.setAnimation(R.raw.ic_night) }
                                    1003, 1006 -> { animationWeather.setAnimation(R.raw.ic_cloudy_night) }
                                    1009, 1030 -> { animationWeather.setAnimation(R.raw.ic_mist) }
                                    1066, 1069, 1072, 1210, 1213, 1216 -> { animationWeather.setAnimation(R.raw.ic_snow_night) }
                                    1114, 1117, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264 -> { animationWeather.setAnimation(R.raw.ic_snow) }
                                    1135, 1147 -> { animationWeather.setAnimation(R.raw.ic_windy) }
                                    1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189,
                                    1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246,
                                    1249, 1252 -> { animationWeather.setAnimation(R.raw.ic_rainy_night) }
                                    1273, 1276, 1279, 1282 -> { animationWeather.setAnimation(R.raw.ic_storm) }
                                }
                                animationWeather.playAnimation()
                            }
                        }
                        resource.data?.location?.let { location -> binding.textView.text = location.name }

                        resource.data?.forecast?.forecastday?.component1()?.let { forecastday ->
                            val chanceRain = "${forecastday.day.dailyChanceOfRain}%"
                            val date = forecastday.date

                            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                            val dateFormat: DateFormat = SimpleDateFormat("EEEE, dd MMMM")
                            val newDate: String = dateFormat.format(df.parse(date))

                            binding.apply {
                                tvDate.text = newDate
                                total.text = chanceRain
                            }
                        }

                        fun menuCelsius(){
                            viewModel.updateUnitPreference(UnitPreference.CELSIUS)
                            resource.data?.current.let { weather ->
                                binding.apply {
                                    num.text ="${weather?.tempC}°C"
                                    celci.text= getString(R.string.celci)
                                }
                            }
                        }

                        fun menuFarenheit(){
                            viewModel.updateUnitPreference(UnitPreference.FAHRENHEIT)
                            resource.data?.current.let { weather ->
                                binding.apply {
                                    binding.num.text = "${weather?.tempF}°F"
                                    binding.celci.text= getString(R.string.fahrenheit)
                                }
                            }
                        }

                        fun menuKelvin(){
                            viewModel.updateUnitPreference(UnitPreference.KELVIN)
                            resource.data?.current.let { weather ->
                                binding.apply {
                                    num.text = "${weather?.tempC?.toInt()?.plus(273)} K"
                                    binding.celci.text= getString(R.string.kelvin)
                                }
                            }
                        }

                    val unitPreferenceEvent = arguments?.getString("unitPreferenceEvent")
                    when (unitPreferenceEvent){
                        "CELSIUS" -> menuCelsius()
                        "FAHRENHEIT" -> menuFarenheit()
                        "KELVIN" -> menuKelvin()
                        else -> menuCelsius()
                    }

                        binding.menu.setOnClickListener {
                            val popupMenu = PopupMenu(this.context, binding.menu)
                            popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
                            popupMenu.setOnMenuItemClickListener { menuItem ->
                                if (menuItem.itemId == R.id.iCelcius) {
                                    menuCelsius()
                                    true
                                } else if (menuItem.itemId == R.id.iFahrenheit) {
                                    menuFarenheit()
                                    true
                                } else if (menuItem.itemId == R.id.iKelvin) {
                                    menuKelvin()
                                    true
                                } else false
                            }
                            popupMenu.show()
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
