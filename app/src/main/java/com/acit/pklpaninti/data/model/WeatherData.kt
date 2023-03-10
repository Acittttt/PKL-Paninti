package com.acit.pklpaninti.data.model

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)