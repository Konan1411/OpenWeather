/**
 * Defines the MyCitiesViewModel class, which inherits from AndroidViewModel.
 * Manage UI data related to MyCities entities.
 * Provides methods to insert, delete and get all cities.
 */

package com.example.weather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.data.AppDatabase
import com.example.weather.data.MyCities
import com.example.weather.data.MyCitiesRepository
import kotlinx.coroutines.launch

class MyCitiesViewModel(application: Application) : AndroidViewModel(application) {
    private val myCityRepository = MyCitiesRepository(
        AppDatabase.getInstance(application).myCitiesDao()
    )

    val myCities_List = myCityRepository.getAllCities().asLiveData()

    fun addMyCities(city: MyCities) {
        viewModelScope.launch {
            myCityRepository.insertMyCities(city)
        }
    }

    fun removeMyCities(city: MyCities) {
        viewModelScope.launch {
            myCityRepository.deleteMyCities(city)
        }
    }
}