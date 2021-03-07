package com.gbeho.weatherapplication.model

//Bean API -> https://openweathermap.org/current
//Exemple -> http://api.openweathermap.org/data/2.5/weather?q=paris&lang=fr&appid=b80967f0a6bd10d23e44848547b26550

data class WeatherBean (var coord: CoordBean, var weather: ArrayList<WeatherContentBean>, var base: String, var main: MainBean,
                        var visibility: Long, var wind: WindBean, var clouds: CloudBean, var dt: Long, var sys: SysBean, var timezone: Int,
                        var id: Long, var name: String, var cod: Int)

data class CoordBean (var lon: Double, var lat: Double)

data class WeatherContentBean (var id: Int, var main: String, var description: String, var icon: String)

data class MainBean (var temp: Double, var feels_like: Double, var temp_min: Double, var temp_max: Double, var pressure: Int, var humidity: Int)

data class WindBean (var speed: Double, var deg: Int, var gust: Double)

data class CloudBean (var all: Int)

data class SysBean (var type: Int, var id: Int, var country: String, var sunrise: Long, var sunset: Long)


// Hourly weather api -> http://api.openweathermap.org/data/2.5/forecast?q=toulouse&appid=b80967f0a6bd10d23e44848547b26550&cnt=16&lang=fr

data class ListWeatherBean (var cod: String, var message: Int, var cnt: Int, var list: ArrayList<CurrentWeatherBean>, var city :CityWeatherBean)
data class CurrentWeatherBean (var dt: Long, var main: MainBean, var weather: ArrayList<WeatherContentBean>, var clouds: CloudBean, var wind: WindBean, var visibility: Long, var pop: Double, var sys: SysBean, var dt_txt: String)
data class CityWeatherBean (var id: Long, var name: String, var coord: CoordBean, var country: String, var population: Long, var timezone: Long, var sunrise: Long, var sunset: Long)