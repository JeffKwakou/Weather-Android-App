package com.gbeho.weatherapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.gbeho.weatherapplication.model.CurrentWeatherBean
import com.gbeho.weatherapplication.outilsAndroid.CityAdapter
import com.gbeho.weatherapplication.outilsAndroid.WeatherAdapter
import com.gbeho.weatherapplication.utils.WSUtils
import kotlinx.android.synthetic.main.activity_display_weather.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import kotlin.concurrent.thread

class DisplayWeatherActivity : AppCompatActivity() {

    //Constante
    val KEY_INTENT_PLACE = "place"
    val KEY_INTENT_ZIP = "city"
    val KEY_INTENT_POSITION_LAT = "lat"
    val KEY_INTENT_POSITION_LON = "long"

    //Outils
    var thread: Thread? = null
    var thread2: Thread? = null

    // Outils recycler weather
    var data = ArrayList<CurrentWeatherBean>()
    val adapter = WeatherAdapter(data)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_weather)

        // Déclaration de l'adapteur pour la météo par heure
        rvWeather.adapter = adapter
        rvWeather.layoutManager = GridLayoutManager(this, 6)

        //Récupérer la recherche de l'edit text
        val intent = intent

        //Lancer la requete de la ville si la clé est une ville
        if (intent.getStringExtra(KEY_INTENT_PLACE) != null) {
            var cityPlace = intent.getStringExtra(KEY_INTENT_PLACE) ?: ""

            //Lance la requête
            threadWeather(cityPlace)
            threadHourly(cityPlace)
        }
        //Si la clé est un zip lancer la requête pour le zip
        else if (intent.getStringExtra(KEY_INTENT_ZIP) != null) {
            var cityZip = intent.getStringExtra(KEY_INTENT_ZIP) ?: ""

            //Lance la requête
            threadWeather(cityZip)
            threadHourly(cityZip)
        }
        //Si la clé est une position lancer la requête pour le zip
        else if (intent.getStringExtra(KEY_INTENT_POSITION_LAT) != null && intent.getStringExtra(KEY_INTENT_POSITION_LON) != null) {
            var cityLat = intent.getStringExtra(KEY_INTENT_POSITION_LAT) ?: ""
            var cityLong = intent.getStringExtra(KEY_INTENT_POSITION_LON) ?: ""

            //Lance la requête
            threadWeatherPosition(cityLat, cityLong)
        }
    }

    //Méthode avec un thread pour la météo heure
    fun threadHourly(place: String) {
        if (thread2 == null || thread2!!.state == Thread.State.TERMINATED) {
            //Lancer le thread
            thread2 = thread {
                try {
                    val dataHourlyWeather = WSUtils.getHourlyWeather(place).list
                    data.clear()
                    data.addAll(dataHourlyWeather)
                    Log.w("TAG", "Le thread a été lancé")
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                        findViewById<TextView>(R.id.tvHoraire).visibility = View.VISIBLE
                    }

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this, "Météo horaire indisponible", Toast.LENGTH_LONG).show()
                        findViewById<TextView>(R.id.tvHoraire).visibility = View.GONE
                    }
                }
            }
        }
    }


    //Méthode pour créer un thread et faire la requête pour le lieu
    fun threadWeather(place: String) {

        if (thread == null || thread!!.state == Thread.State.TERMINATED) {
            //Lancer le thread
            thread = thread {

                //Gérer l'exception de la requête si erreur
                try {
                    val weatherData = WSUtils.getWeather(place)

                    //Je récupère les objets avec les data utiles
                    val main = weatherData.main
                    val wind = weatherData.wind
                    val test = weatherData.weather[0]

                    //Pour afficher les doubles avec 2 chiffres arpès la virgule
                    var df = DecimalFormat("0.00")

                    //Afficher les data dans les text view
                    runOnUiThread {
                        tvName.text = weatherData.name
                        tvDescription.text = test.description
                        tvHumidity.text = "" + main.humidity + "%"
                        tvTemp.text = "" + (main.temp - 273.15).toInt() + "°"
                        tvFeelsLike.text = "" + (main.feels_like - 273.15).toInt() + "°"
                        tvVisibility.text = "" + (weatherData.visibility / 1000) + " km"
                        tvWind.text = "" + df.format((wind.speed * 3.6)) + " km/h"
                        tvPressure.text = "" + main.pressure + " hPa"
                    }
                }catch (e: Exception) {
                    e.printStackTrace()
                    //Afficher un Toast si erreur
                    runOnUiThread {
                        Toast.makeText(this, "Ville introuvable", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        this.startActivity(intent)
                    }
                }

            }
        }
    }

    //Méthode pour créer un thread et faire la requête pour la position
    fun threadWeatherPosition(lat: String, long: String) {

        if (thread == null || thread!!.state == Thread.State.TERMINATED) {
            //Lancer le thread
            thread = thread {

                //Gérer l'exception de la requête si erreur
                try {
                    val weatherData = WSUtils.getWeatherPosition(lat, long)

                    //Je récupère les objets avec les data utiles
                    val main = weatherData.main
                    val wind = weatherData.wind
                    val test = weatherData.weather[0]

                    //Pour afficher les doubles avec 2 chiffres arpès la virgule
                    var df = DecimalFormat("0.00")

                    //Lance la requête pour la météo heure
                    WSUtils.getHourlyWeather(weatherData.name)

                    //Afficher les data dans les text view
                    runOnUiThread {
                        tvName.text = weatherData.name
                        tvDescription.text = test.description
                        tvHumidity.text = "" + main.humidity + "%"
                        tvTemp.text = "" + (main.temp - 273.15).toInt() + "°"
                        tvFeelsLike.text = "" + (main.feels_like - 273.15).toInt() + "°"
                        tvVisibility.text = "" + (weatherData.visibility / 1000) + " km"
                        tvWind.text = "" + df.format((wind.speed * 3.6)) + " km/h"
                        tvPressure.text = "" + main.pressure + " hPa"
                    }
                }catch (e: Exception) {
                    e.printStackTrace()
                    //Afficher un Toast si erreur
                    runOnUiThread {
                        Toast.makeText(this, "Ville introuvable", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        this.startActivity(intent)
                    }
                }

            }
        }
    }

}