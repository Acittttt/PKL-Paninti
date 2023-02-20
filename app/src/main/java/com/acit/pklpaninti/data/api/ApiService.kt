package com.acit.pklpaninti.data.api

import com.acit.pklpaninti.data.model.WeatherData
import com.acit.pklpaninti.utils.UserPath
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(UserPath.USER_PATH)
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") cityName : String,
        @Query("days") days: Int,
        @Query("aqi") aqi : String,
        @Query("alerts") alerts: String
    ) : WeatherData
}

