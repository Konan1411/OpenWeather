package com.example.weather.ui

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.example.weather.R
import com.example.weather.`data`.ForecastCity
import com.example.weather.`data`.ForecastPeriod
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

public class ForecastListFragmentDirections private constructor() {
  private data class NavigateToForecastDetail(
    public val forecast: ForecastPeriod,
    public val city: ForecastCity
  ) : NavDirections {
    public override val actionId: Int = R.id.navigate_to_forecast_detail

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
        val result = Bundle()
        if (Parcelable::class.java.isAssignableFrom(ForecastPeriod::class.java)) {
          result.putParcelable("forecast", this.forecast as Parcelable)
        } else if (Serializable::class.java.isAssignableFrom(ForecastPeriod::class.java)) {
          result.putSerializable("forecast", this.forecast as Serializable)
        } else {
          throw UnsupportedOperationException(ForecastPeriod::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (Parcelable::class.java.isAssignableFrom(ForecastCity::class.java)) {
          result.putParcelable("city", this.city as Parcelable)
        } else if (Serializable::class.java.isAssignableFrom(ForecastCity::class.java)) {
          result.putSerializable("city", this.city as Serializable)
        } else {
          throw UnsupportedOperationException(ForecastCity::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        return result
      }
  }

  public companion object {
    public fun navigateToForecastDetail(forecast: ForecastPeriod, city: ForecastCity): NavDirections
        = NavigateToForecastDetail(forecast, city)
  }
}
