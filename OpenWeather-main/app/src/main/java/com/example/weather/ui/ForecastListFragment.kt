/**
 * A fragment showing all weather forecast data.
 * Observe the ViewModel's forecast data and update the UI.
 * Handle city click events to display detailed information or view city locations on the map.
 */

package com.example.weather.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.data.ForecastCity
import com.example.weather.data.ForecastPeriod
import com.example.weather.data.MyCities
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar

class ForecastListFragment: Fragment(R.layout.forecast_list_screen) {
    private val TAG = "MainActivity"

    private val viewModel: FiveDayForecastViewModel by viewModels()
    private val forecastAdapter = ForecastAdapter(::onForecastItemClick)

    private val myCitiesviewModel: MyCitiesViewModel by viewModels()

    private lateinit var forecastListRV: RecyclerView
    private lateinit var loadingErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        loadingErrorTV = view.findViewById(R.id.tv_loading_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        /*
         * Set up RecyclerView.
         */
        forecastListRV = view.findViewById(R.id.rv_forecast_list)
        forecastListRV.layoutManager = LinearLayoutManager(requireContext())
        forecastListRV.setHasFixedSize(true)
        forecastListRV.adapter = forecastAdapter

        /*
         * Set up an observer on the current forecast data.  If the forecast is not null, display
         * it in the UI.
         */
        viewModel.forecast.observe(viewLifecycleOwner) { forecast ->
            if (forecast != null) {
                forecastAdapter.updateForecast(forecast)
                forecastListRV.visibility = View.VISIBLE
                forecastListRV.scrollToPosition(0)
                (requireActivity() as AppCompatActivity).supportActionBar?.title = forecast.city.name
            }
        }

        /*
         * Set up an observer on the error associated with the current API call.  If the error is
         * not null, display the error that occurred in the UI.
         */
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                loadingErrorTV.text = getString(R.string.loading_error, error.message)
                loadingErrorTV.visibility = View.VISIBLE
                Log.e(tag, "Error fetching forecast: ${error.message}")
            }
        }

        /*
         * Set up an observer on the loading status of the API query.  Display the correct UI
         * elements based on the current loading status.
         */
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                loadingIndicator.visibility = View.VISIBLE
                loadingErrorTV.visibility = View.INVISIBLE
                forecastListRV.visibility = View.INVISIBLE
            } else {
                loadingIndicator.visibility = View.INVISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchForecast()
    }

    private fun fetchForecast() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val city = sharedPrefs.getString(getString(R.string.pref_city_key), "Belfort,France")
        val units = sharedPrefs.getString(getString(R.string.pref_units_key), null)

        viewModel.loadFiveDayForecast(city, units, OPENWEATHER_APPID)
    }


    /**
     * This method is passed into the RecyclerView adapter to handle clicks on individual items
     * in the list of forecast items.  When a forecast item is clicked, a new activity is launched
     * to view its details.
     */
    private fun onForecastItemClick(forecastPeriod: ForecastPeriod) {
        Log.d(TAG, "onForecastItemClick() called, forecastPeriod : $forecastPeriod")
        val directions = ForecastListFragmentDirections.navigateToForecastDetail(
            forecastPeriod,
            forecastAdapter.forecastCity!!
        )
        findNavController().navigate(directions)
    }

    /**
     * This method creates an implicit intent to display the current forecast location in a map.
     */
    private fun viewForecastCityOnMap() {
        if (forecastAdapter.forecastCity != null) {
            val geoUri = Uri.parse(getString(
                R.string.geo_uri,
                forecastAdapter.forecastCity?.lat ?: 0.0,
                forecastAdapter.forecastCity?.lon ?: 0.0,
                11
            ))
            val intent = Intent(Intent.ACTION_VIEW, geoUri)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                view?.let{
                    Snackbar.make(
                        it.findViewById(R.id.coordinator_layout),
                        R.string.action_map_error,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}