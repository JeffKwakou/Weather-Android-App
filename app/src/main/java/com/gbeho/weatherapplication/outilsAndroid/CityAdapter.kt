package com.gbeho.weatherapplication.outilsAndroid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gbeho.weatherapplication.DisplayWeatherActivity
import com.gbeho.weatherapplication.MainActivity
import com.gbeho.weatherapplication.R
import com.gbeho.weatherapplication.model.CityBean
import java.util.ArrayList

class CityAdapter(val data: ArrayList<CityBean>) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    //Pointeur vers les composants graphiques de la ligne à dupliquer
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv1: TextView = itemView.findViewById(R.id.tv_ville)
        val tv2: TextView = itemView.findViewById(R.id.tv_cp)
        val root: LinearLayout = itemView.findViewById(R.id.root)
    }

    //Création d'une instance du XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_city_zip, null)
        return ViewHolder(view)
    }

    //Remplir une instance
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//Donnée
        val datum = data[position]
        holder.tv1.text = datum.ville
        holder.tv2.text = datum.cp

        // Callback clic sur un élément
        holder.root.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.tv2.context, DisplayWeatherActivity::class.java)
            intent.putExtra("city", datum.ville)
            holder.tv2.context.startActivity(intent)
        })
    }

    //Combien d'élément dans mes données
    override fun getItemCount() = data.size
}