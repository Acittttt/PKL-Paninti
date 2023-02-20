package com.acit.pklpaninti.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acit.pklpaninti.R
import com.acit.pklpaninti.data.model.Hour
import com.acit.pklpaninti.data.model.WeatherData
import com.acit.pklpaninti.databinding.FragmentListItemBinding
import com.bumptech.glide.Glide

class WeatherHomeAdapter: RecyclerView.Adapter<WeatherHomeAdapter.WeatherHomeViewHolder>() {

    class WeatherHomeViewHolder(private val binding: FragmentListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Hour) {
            binding.apply {
                val humidity = "${item.humidity}%"
                val hour = "${item.time.substring(11, 16)}"
                val iconWeather = "https:${item.condition.icon}"

                Glide.with(icon.context)
                    .load(iconWeather)
                    .into(icon)

                percen.text = humidity
                time.text = hour
            }
        }
    }

    val differCallback = object : DiffUtil.ItemCallback<Hour>() {
        override fun areItemsTheSame(
            oldItem: Hour,
            newItem: Hour
        ) = oldItem.time == newItem.time

        override fun areContentsTheSame(
            oldItem: Hour,
            newItem: Hour
        ) = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
    var items : List<Hour>
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

    override fun getItemCount() = items.size
}