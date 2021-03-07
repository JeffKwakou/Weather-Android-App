package com.gbeho.weatherapplication.outilsAndroid

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gbeho.weatherapplication.R
import com.gbeho.weatherapplication.model.CurrentWeatherBean
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherAdapter(val data: ArrayList<CurrentWeatherBean>): RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv1: TextView = itemView.findViewById(R.id.tv_heure)
        val tv2: TextView = itemView.findViewById(R.id.tv_etat)
        val tv3: TextView = itemView.findViewById(R.id.tv_temperature)
        val tv4: TextView = itemView.findViewById(R.id.tv_humidite)
    }

    //Création d'une instance du XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_weather, null)
        return ViewHolder(view)
    }

    // Combien il y a d'éléments
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datum = data[position]
        var formatter = SimpleDateFormat("HH:mm")
        var parser = SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss")
        var output = formatter.format(parser.parse(datum.dt_txt))
        holder.tv1.text = output
        holder.tv2.text = datum.weather[0].description
        holder.tv3.text = "" + (datum.main.temp - 273.15).toInt() + "°"
        holder.tv4.text = "" + datum.main.humidity + "%"
    }
}