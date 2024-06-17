package com.example.weather.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.weather.data.AppDatabase
import com.example.weather.data.MyCities
import com.example.weather.data.MyCitiesRepository
import com.example.weather.data.User
import com.example.weather.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyCitiesViewModel(application: Application) : AndroidViewModel(application) {
    private val myCityRepository = MyCitiesRepository(
        AppDatabase.getInstance(application).myCitiesDao()
    )

    private val _userId = MutableLiveData<Int>()

    val myCities_List: LiveData<List<MyCities>> = _userId.switchMap { userId ->
        if (userId != -1) {
            myCityRepository.getCitiesForUser(userId).asLiveData()
        } else {
            MutableLiveData(emptyList())
        }
    }

    fun setUserId(userId: Int) {
        _userId.value = userId
    }

    fun addMyCities(city: MyCities) {
        viewModelScope.launch {
            val existingCity = myCityRepository.getCityForUser(city.user, city.city)
            if (existingCity == null) {
                myCityRepository.insertMyCity(city)
            }
        }
    }

    fun removeMyCities(city: MyCities) {
        viewModelScope.launch {
            myCityRepository.deleteMyCity(city)
        }
    }

    suspend fun checkCity(city: String, userId: Int): MyCities? {
        return withContext(Dispatchers.IO) {
            myCityRepository.getCityForUser(userId, city)
        }
    }
}
