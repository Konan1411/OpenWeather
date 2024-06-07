package com.example.weather.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.weather.ui.UserViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class SessionManager(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean> get() = _isUserLoggedIn

    init {
        _isUserLoggedIn.value = isUserLoggedIn()
    }
    fun isUserLoggedIn(): Boolean {
        return sharedPref.getInt("user_id", -1) != -1
    }

    fun getUserId(): Int {
        return sharedPref.getInt("user_id", -1)
    }

    fun loginUser(userId: Int) {
        editor.putInt("user_id", userId)
        editor.apply()
    }

    fun logoutUser() {
        editor.clear().apply()
   }

}
