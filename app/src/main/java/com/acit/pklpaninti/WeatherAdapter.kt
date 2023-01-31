package com.acit.pklpaninti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.imageview.ShapeableImageView

class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val icon : ImageView = itemView.findViewById(R.id.icon)
        val day : TextView = itemView.findViewById(R.id.day)
        val wheat : TextView = itemView.findViewById(R.id.weather)
        val temp : TextView = itemView.findViewById(R.id.temp)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Weather>(){
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.icon.setImageResource(currentItem.icon)
        holder.day.text = currentItem.day
        holder.wheat.text = currentItem.wheat
        holder.temp.text = currentItem.temp
    }
}