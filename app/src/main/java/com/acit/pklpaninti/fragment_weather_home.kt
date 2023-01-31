package com.acit.pklpaninti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acit.pklpaninti.databinding.FragmentWeatherBinding
import com.acit.pklpaninti.databinding.FragmentWeatherHomeBinding


class fragment_weather_home : Fragment() {

    private var _binding: FragmentWeatherHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : WeatherHomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var weatherHomeArrayList: ArrayList<WeatherHome>

    lateinit var icon : Array<Int>
    lateinit var time : Array<String>
    lateinit var percen : Array<String>


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
        _binding = FragmentWeatherHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        cardView()
        binding.dys.setOnClickListener {
            val fragment = fragment_weather()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, fragment)?.commit()
        }
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
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = WeatherHomeAdapter()
        recyclerView.adapter = adapter
    }

    private fun dataInitialize() {

        weatherHomeArrayList = arrayListOf<WeatherHome>()

        icon = arrayOf(
            R.drawable.icon_cloud,
            R.drawable.icon_dry,
            R.drawable.icon_strom,
            R.drawable.icon_rainy,
        )

        time = arrayOf(
            getString(R.string.time2),
            getString(R.string.time),
            getString(R.string.time3),
            getString(R.string.time4),
        )

        percen = arrayOf(
            getString(R.string.persen),
            getString(R.string.persen),
            getString(R.string.persen),
            getString(R.string.persen),
        )

        for (i in icon.indices) {

            val weatherHome = WeatherHome(icon[i], percen[i], time[i])
            weatherHomeArrayList.add(weatherHome)
        }
    }

}