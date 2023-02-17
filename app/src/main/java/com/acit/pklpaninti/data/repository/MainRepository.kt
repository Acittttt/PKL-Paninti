package com.acit.pklpaninti.data.repository

import com.acit.pklpaninti.data.api.ApiHelper
class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getForecast() = apiHelper.getForecast()
}