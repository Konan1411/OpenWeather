// com/example/weather/ui/UserViewModel.kt
package com.example.weather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.data.AppDatabase
import com.example.weather.data.User
import com.example.weather.data.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository

    init {
        val userDao = AppDatabase.getInstance(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun getUser(username: String, password: String){
        viewModelScope.launch {
            userRepository.getUser(username, password)        }
    }

    suspend fun getUserByUsername(username: String): User? {
        return userRepository.getUserByUsername(username)
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun updatePassword(username: String, password: String){
        viewModelScope.launch {
            userRepository.updatePassword(username, password)
        }
    }
}
