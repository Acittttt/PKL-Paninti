package com.acit.pklpaninti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import com.google.android.material.imageview.ShapeableImageView

class WeatherAdapter (private val weatherList : ArrayList<Weather>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_list,parent,false)
        return WeatherViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentItem = weatherList[position]
        holder.icon.setImageResource(currentItem.icon)
        holder.day.text = currentItem.day
        holder.wheat.text = currentItem.wheat
        holder.temp.text = currentItem.temp
    }

    class WeatherViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val icon : ImageView = itemView.findViewById(R.id.icon)
        val day : TextView = itemView.findViewById(R.id.day)
        val wheat : TextView = itemView.findViewById(R.id.weather)
        val temp : TextView = itemView.findViewById(R.id.temp)
    }
}