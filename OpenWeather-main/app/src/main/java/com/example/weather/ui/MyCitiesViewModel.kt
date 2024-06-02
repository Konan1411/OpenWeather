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

    // Supposons que vous ayez un moyen d'obtenir l'userId à partir de l'application, 
    // par exemple à partir d'une session utilisateur.
    private val userId: Int = 1

    val myCities_List = myCityRepository.getCitiesForUser(userId).asLiveData()

    fun addMyCities(city: MyCities) {
        viewModelScope.launch {
            myCityRepository.insertMyCity(city)
        }
    }

    fun removeMyCities(city: MyCities) {
        viewModelScope.launch {
            myCityRepository.deleteMyCity(city)
        }
    }
}
