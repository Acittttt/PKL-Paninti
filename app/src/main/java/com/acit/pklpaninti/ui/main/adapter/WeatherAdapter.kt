package com.acit.pklpaninti.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.acit.pklpaninti.data.model.Forecastday
import com.acit.pklpaninti.databinding.FragmentListBinding
import com.acit.pklpaninti.ui.main.viewmodel.MainViewModel
import com.acit.pklpaninti.utils.UnitPreference
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.text.SimpleDateFormat

class WeatherAdapter(private val viewModel: MainViewModel): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(private val binding: FragmentListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Forecastday) {
            binding.apply {

                val condition = "${item.day.condition.text}"
                val image = "https:${item.day.condition.icon}"
                val day = item.date
                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateFormat: DateFormat = SimpleDateFormat("EEEE")
                val newDay: String = dateFormat.format(df.parse(day))

                val unitPreference = viewModel.unitPreference.value
                val temperature = when (unitPreference) {
                    UnitPreference.CELSIUS -> item?.day?.avgtempC?.toInt()
                    UnitPreference.FAHRENHEIT -> item?.day?.avgtempF?.toInt()
                    UnitPreference.KELVIN -> item?.day?.avgtempC?.toInt()?.plus(273)
                    else -> {item?.day?.avgtempC?.toInt()}
                }
                val temperatureText = when (unitPreference) {
                    UnitPreference.CELSIUS -> "$temperature°C"
                    UnitPreference.FAHRENHEIT -> "$temperature°F"
                    UnitPreference.KELVIN -> "$temperature K"
                    else -> {"$temperature°"}
                }

                Glide.with(icon.context)
                    .load(image)
                    .into(icon)

                binding.day.text = newDay
                binding.weather.text = condition
                binding.temp.text = temperatureText
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(
            oldItem: Forecastday,
            newItem: Forecastday
        ) = oldItem.date == newItem.date

        override fun areContentsTheSame(
            oldItem: Forecastday,
            newItem: Forecastday
        ) = oldItem == newItem
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
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount() = items.size
}