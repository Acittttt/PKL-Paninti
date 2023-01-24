package com.acit.pklpaninti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acit.pklpaninti.databinding.FragmentLoginBinding
import com.acit.pklpaninti.databinding.FragmentWeatherBinding




class fragment_weather : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : WeatherAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var weatherArrayList: ArrayList<Weather>

    lateinit var icon : Array<Int>
    lateinit var day : Array<String>
    lateinit var wheat : Array<String>
    lateinit var temp : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val view = binding.root
        cardView()
        return view
    }

    private fun cardView(){
        binding.header.setBackgroundResource(R.drawable.bg_weather)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = WeatherAdapter(weatherArrayList)
        recyclerView.adapter = adapter
    }

    private fun dataInitialize(){

        weatherArrayList = arrayListOf<Weather>()

        icon = arrayOf(
            R.drawable.icon_cloud,
            R.drawable.icon_cloud,
            R.drawable.icon_rainy,
            R.drawable.icon_strom,
            R.drawable.icon_sunny,
            R.drawable.icon_sunny,
            R.drawable.icon_sunny
        )

        day = arrayOf(
            getString(R.string.day1),
            getString(R.string.day2),
            getString(R.string.day3),
            getString(R.string.day4),
            getString(R.string.day5),
            getString(R.string.day6),
            getString(R.string.day7),
        )

        wheat = arrayOf(
            getString(R.string.weather),
            getString(R.string.weather),
            getString(R.string.wheat2),
            getString(R.string.wheat3),
            getString(R.string.wheat4),
            getString(R.string.wheat4),
            getString(R.string.wheat4),
        )

        temp = arrayOf(
            getString(R.string.temp1),
            getString(R.string.temp2),
            getString(R.string.temp3),
            getString(R.string.temp4),
            getString(R.string.temp5),
            getString(R.string.temp6),
            getString(R.string.temp7),
        )

        for (i in icon.indices){

            val weather = Weather(icon[i], day[i], wheat[i], temp[i])
            weatherArrayList.add(weather)
        }

    }
}