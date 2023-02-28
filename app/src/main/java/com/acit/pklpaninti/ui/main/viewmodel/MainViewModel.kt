package com.acit.pklpaninti.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.acit.pklpaninti.data.repository.MainRepository
import com.acit.pklpaninti.utils.Resource
import com.acit.pklpaninti.utils.UnitPreference
import kotlinx.coroutines.Dispatchers
class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _unitPreference = MutableLiveData<UnitPreference>()
    val unitPreference: LiveData<UnitPreference>
        get() = _unitPreference

    fun updateUnitPreference(unitPreference: UnitPreference) {
        _unitPreference.value = unitPreference
    }
    fun getForecast() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getForecast()))
        } catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
}