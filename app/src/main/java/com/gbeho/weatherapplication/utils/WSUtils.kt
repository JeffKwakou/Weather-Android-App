package com.gbeho.weatherapplication.utils

import com.gbeho.weatherapplication.model.CityBean
import com.gbeho.weatherapplication.model.ListWeatherBean
import com.gbeho.weatherapplication.model.ResultBean
import com.gbeho.weatherapplication.model.WeatherBean
import com.google.gson.Gson

val url = "http://api.openweathermap.org/data/2.5/weather?lang=fr&appid=b80967f0a6bd10d23e44848547b26550&q="
val url2 = "http://api.openweathermap.org/data/2.5/weather?lang=fr&appid=b80967f0a6bd10d23e44848547b26550&zip="
val url3 = "http://api.openweathermap.org/data/2.5/weather?lang=fr&appid=b80967f0a6bd10d23e44848547b26550&lat="
val url4 = "http://www.citysearch-api.com/fr/city?login=webserviceexemple&apikey=sof940dd26cf107eabf8bf6827f87c3ca8e8d82546&cp="
val url5 = "http://api.openweathermap.org/data/2.5/forecast?appid=b80967f0a6bd10d23e44848547b26550&cnt=6&lang=fr&q="

class WSUtils {
    companion object {
        fun getHourlyWeather(place: String): ListWeatherBean {
            //Récupérer les données de la requête
            val request = OkhttpUtils.sendGetOkHttpRequest(url5 + place)

            //Créer le gson
            val gson = Gson()

            //Parser les données de la requête en objet WeatherBean
            val HourlyResult = gson.fromJson(request, ListWeatherBean::class.java)

            return HourlyResult
        }

        fun getWeather(place: String): WeatherBean {
            //Récupérer les données de la requête
            val request = OkhttpUtils.sendGetOkHttpRequest(url + place)

            //Créer le gson
            val gson = Gson()

            //Parser les données de la requête en objet WeatherBean
            val weatherResult = gson.fromJson(request, WeatherBean::class.java)

            return weatherResult
        }

        fun getWeatherZip(zip: String): WeatherBean {
            //Récupérer les données de la requête
            val request = OkhttpUtils.sendGetOkHttpRequest(url2 + zip + ",fr")

            //Créer le gson
            val gson = Gson()

            //Parser les données de la requête en objet WeatherBean
            val weatherResult = gson.fromJson(request, WeatherBean::class.java)

            return weatherResult
        }

        fun getWeatherPosition(lat: String, long: String): WeatherBean {
            //Récupérer les données de la requête
            val request = OkhttpUtils.sendGetOkHttpRequest(url3 + lat + "&lon=" + long)

            //Créer le gson
            val gson = Gson()

            //Parser les données de la requête en objet WeatherBean
            val weatherResult = gson.fromJson(request, WeatherBean::class.java)

            return weatherResult
        }

        fun getCities(cp: String): ArrayList<CityBean> {
            //Récupérer les données de ma requete dans un ArrayList
            var request = OkhttpUtils.sendGetOkHttpRequest(url4 + cp)
            val gson = Gson()
            val result = gson.fromJson(request, ResultBean::class.java)

            //Check si il y a une erreur -> throw Exception
            if (result.errors != null) {
                throw Exception(result.errors.message)
            } else if (result.results.isNullOrEmpty()) {
                throw Exception("La liste est vide")
            } else {
                return result.results
            }
        }
    }
}