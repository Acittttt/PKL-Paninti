package com.acit.pklpaninti.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.acit.pklpaninti.R
import com.acit.pklpaninti.data.model.WeatherData
import com.acit.pklpaninti.databinding.FragmentListBinding

class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val limit = 7

    class WeatherViewHolder(private val binding: FragmentListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: WeatherData) {
            binding.apply {
                val day = "Monday"
                val main = "${weather.weather.component1().main}"
                val temp = "${weather.main.temp.toInt()}°"

                if(main == "Clouds") {
                    icon.setImageResource(R.drawable.icon_cloud)
                    binding.weather.text = "Cloudy"
                } else if (main == "Rain") {
                    icon.setImageResource(R.drawable.icon_rainy)
                    binding.weather.text = "Rainy"
                } else if (main == "Clear") {
                    icon.setImageResource(R.drawable.icon_sunny)
                    binding.weather.text = "Sunny"
                } else {
                    icon.setImageResource(R.drawable.icon_cloud)
                    binding.weather.text = "Cloudy"
                }

                binding.day.text = day
                binding.temp.text = temp
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.dtTxt.substring(0, 10) == newItem.dtTxt.substring(0, 10)
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    var items : List<WeatherData>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeatherViewHolder(
        FragmentListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(items[position])
        holder.setIsRecyclable(true)
    }
    override fun getItemCount(): Int {
        return if (items.size > limit) limit else items.size
    }
}
