package com.acit.pklpaninti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherHomeAdapter (private val weatherHomeList : ArrayList<WeatherHome>) : RecyclerView.Adapter<WeatherHomeAdapter.WeatherHomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item,parent,false)
        return WeatherHomeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return weatherHomeList.size
    }

    override fun onBindViewHolder(holder: WeatherHomeViewHolder, position: Int) {
        val currentItem = weatherHomeList[position]
        holder.icon.setImageResource(currentItem.icon)
        holder.percen.text = currentItem.percen
        holder.time.text = currentItem.time
    }

    class WeatherHomeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val icon : ImageView = itemView.findViewById(R.id.icon)
        val time : TextView = itemView.findViewById(R.id.time)
        val percen : TextView = itemView.findViewById(R.id.percen)
    }
}