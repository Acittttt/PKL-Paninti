package com.acit.pklpaninti.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.acit.pklpaninti.data.repository.MainRepository
import com.acit.pklpaninti.utils.Resource
import kotlinx.coroutines.Dispatchers
class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    fun getForecast() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getForecast()))
        } catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
}