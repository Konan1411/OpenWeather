/**
 * The FiveDayForecast data class is defined for parsing JSON data returned by the OpenWeather API.
 * The @JsonClass annotation is used to generate the adapter for Moshi parsing.
 */

package com.example.weather.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * This class is used to help parse the JSON forecast data returned by the OpenWeather API's
 * 5-day/3-hour forecast.
 */
@JsonClass(generateAdapter = true)
data class FiveDayForecast(
    @Json(name = "list") val periods: List<ForecastPeriod>,
    val city: ForecastCity
)
