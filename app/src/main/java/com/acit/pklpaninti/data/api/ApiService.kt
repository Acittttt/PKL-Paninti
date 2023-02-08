package com.acit.pklpaninti.data.api

import com.acit.pklpaninti.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/forecast")

    suspend fun getForecast(
        @Query("q") cityName : String,
        @Query("appid") apiKey : String,
        @Query("units") units : String,
    ) : Weather
}

