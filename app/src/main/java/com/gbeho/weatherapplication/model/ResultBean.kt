package com.gbeho.weatherapplication.model

data class ResultBean (var results: ArrayList<CityBean>, var nbr: Int, var errors: ErrorBean)