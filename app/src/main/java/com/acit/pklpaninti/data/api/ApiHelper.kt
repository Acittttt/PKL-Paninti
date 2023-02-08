package com.acit.pklpaninti.data.api

class ApiHelper (private val apiService: ApiService) {

    companion object {
        private const val API_KEY = "5241b6dabc2aa8c9497ece64d55527b3"
    }

    suspend fun getForecast() =apiService.getForecast("cimahi", API_KEY, "metric")
}