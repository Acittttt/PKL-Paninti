package com.acit.pklpaninti.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.acit.pklpaninti.R
import com.acit.pklpaninti.data.model.Forecastday
import com.acit.pklpaninti.data.model.WeatherData
import com.acit.pklpaninti.databinding.FragmentListBinding
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.text.SimpleDateFormat

class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val limit = 7

    class WeatherViewHolder(private val binding: FragmentListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Forecastday) {
            binding.apply {
                val condition = "${item.day.condition.text}"
                val temp = "${item.day.avgtempC.toInt()}°"
                val image = "https:${item.day.condition.icon}"
                val day = item.date
                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateFormat: DateFormat = SimpleDateFormat("EEEE")
                val newDay: String = dateFormat.format(df.parse(day))

                Glide.with(icon.context)
                    .load(image)
                    .into(icon)

                binding.day.text = newDay
                binding.weather.text = condition
                binding.temp.text = temp
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    var items : List<Forecastday>
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
