package com.acit.pklpaninti.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.acit.pklpaninti.R
import com.acit.pklpaninti.data.api.ApiHelper
import com.acit.pklpaninti.data.api.RetrofitBuilder
import com.acit.pklpaninti.ui.main.adapter.WeatherAdapter
import com.acit.pklpaninti.databinding.FragmentWeatherBinding
import com.acit.pklpaninti.ui.base.ViewModelFactory
import com.acit.pklpaninti.ui.main.viewmodel.MainViewModel
import com.acit.pklpaninti.utils.Status
import com.acit.pklpaninti.utils.UnitPreference

class FragmentWeather : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: WeatherAdapter

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
        onBackPressed()
    }

    private fun navigateAction(){
        val unitPreferenceEvent = viewModel.unitPreference.value.toString()
        val action = FragmentWeatherDirections.actionFragmentWeatherToFragmentWeatherHome(unitPreferenceEvent)
        findNavController().navigate(action)
    }

    private fun backOnClick(){
        binding.back.setOnClickListener{
            navigateAction()
            }
    }

    private fun onBackPressed(){
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateAction()
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, callback)
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
        adapter = WeatherAdapter(viewModel)
        binding.recyclerView.adapter = adapter
    }

    private fun snowLoading(loading: Boolean) {
        binding.apply {
            animationLoading.isVisible = loading
            recyclerView.isVisible =!loading
        }
    }

    @SuppressLint("NotifyDataSetChanged")
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
                        fun menuCelsius(){
                            viewModel.updateUnitPreference(UnitPreference.CELSIUS)
                            resource.data?.forecast?.forecastday?.component2()?.day.let { weather ->
                                val maxTemp = "${weather?.maxtempC?.toInt()}"
                                val minTemp = "/${weather?.mintempC?.toInt()}°C"
                                binding.apply {
                                    num.text = maxTemp
                                    temp.text= minTemp
                                }
                            }
                        }

                        fun menuFarenheit(){
                            viewModel.updateUnitPreference(UnitPreference.FAHRENHEIT)
                            resource.data?.forecast?.forecastday?.component2()?.day.let { weather ->
                                val maxTemp = "${weather?.maxtempF?.toInt()}"
                                val minTemp = "/${weather?.mintempF?.toInt()}°F"
                                binding.apply {
                                    num.text = maxTemp
                                    temp.text= minTemp
                                }
                            }
                        }

                        fun menuKelvin(){
                            viewModel.updateUnitPreference(UnitPreference.KELVIN)
                            resource.data?.forecast?.forecastday?.component2()?.day.let { weather ->
                                val maxTemp = "${weather?.maxtempC?.toInt()?.plus(273)}"
                                val minTemp = "/${weather?.mintempC?.toInt()?.plus(273)} K"
                                binding.apply {
                                    num.text = maxTemp
                                    temp.text= minTemp
                                }
                            }
                        }

                        val unitPreferenceHome = arguments?.getString("unitPreferenceHome")
                        when (unitPreferenceHome){
                            "CELSIUS" -> menuCelsius()
                            "FAHRENHEIT" -> menuFarenheit()
                            "KELVIN" -> menuKelvin()
                            else -> menuCelsius()
                        }

                        binding.menu.setOnClickListener {
                            val popupMenu = PopupMenu(this.context, binding.menu)
                            popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
                            popupMenu.setOnMenuItemClickListener { menuItem ->
                                when (menuItem.itemId) {
                                    R.id.iCelcius -> {
                                        menuCelsius()
                                        true
                                    }
                                    R.id.iFahrenheit -> {
                                        menuFarenheit()
                                        true
                                    }
                                    R.id.iKelvin -> {
                                        menuKelvin()
                                        true
                                    }
                                    else -> false
                                }
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
