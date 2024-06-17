package com.example.weather.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.weather.R
import com.google.android.material.textview.MaterialTextView


class ForecastDetailActivity : AppCompatActivity() {

    private lateinit var btnExpandMenu: Button
    private var popupWindow: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_detail)

        btnExpandMenu = findViewById(R.id.btn_expand_menu)
        btnExpandMenu.setOnClickListener {
            showPopup()
        }
    }

    private fun showPopup() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_layout, findViewById(android.R.id.content), false)

        Log.d("Clicked", "Clicked")

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        // Set content for the popup views (replace with actual data binding)
        popupView.findViewById<MaterialTextView>(R.id.tv_popup_title).text = "Weather Details"
        popupView.findViewById<MaterialTextView>(R.id.tv_temperature).text = "Temperature: 25°C"
        popupView.findViewById<MaterialTextView>(R.id.tv_humidity).text = "Humidity: 60%"
        popupView.findViewById<MaterialTextView>(R.id.tv_wind_speed).text = "Wind Speed: 10 km/h"

        // Close button in popup
        val btnClose = popupView.findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            popupWindow?.dismiss()
        }

        // Show the popup anchored to the bottom of the activity
        popupWindow?.showAsDropDown(btnExpandMenu)
    }
}