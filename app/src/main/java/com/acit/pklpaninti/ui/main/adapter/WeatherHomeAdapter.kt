package com.acit.pklpaninti.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acit.pklpaninti.R
import com.acit.pklpaninti.data.model.WeatherData
import com.acit.pklpaninti.databinding.FragmentListItemBinding

class WeatherHomeAdapter: RecyclerView.Adapter<WeatherHomeAdapter.WeatherHomeViewHolder>() {

    private val limit = 4

    class WeatherHomeViewHolder(private val binding: FragmentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: WeatherData) {
            binding.apply {
                val humidity = "${weather.main.humidity}%"
                val hour = "${weather.dtTxt.substring(11, 16)}"
                val main = "${weather.weather.component1().main}"

                if(main == "Clouds") icon.setImageResource(R.drawable.icon_two_cloud)
                else if (main == "Rain") icon.setImageResource(R.drawable.icon_rainy)
                else if (main == "Clear") icon.setImageResource(R.drawable.icon_sunny)
                else icon.setImageResource(R.drawable.icon_cloud)
                percen.text = humidity
                time.text = hour
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.dtTxt == newItem.dtTxt
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    var items : List<WeatherData>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeatherHomeViewHolder(
        FragmentListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: WeatherHomeViewHolder, position: Int) {
        holder.bind(items[position])
        holder.setIsRecyclable(true)
    }
    override fun getItemCount(): Int {
        return if (items.size > limit) limit else items.size
    }
}