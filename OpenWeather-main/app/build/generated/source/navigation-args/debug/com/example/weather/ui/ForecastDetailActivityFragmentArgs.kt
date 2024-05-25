package com.example.weather.ui

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.example.weather.`data`.ForecastCity
import com.example.weather.`data`.ForecastPeriod
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class ForecastDetailActivityFragmentArgs(
  public val forecast: ForecastPeriod,
  public val city: ForecastCity
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
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

  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    if (Parcelable::class.java.isAssignableFrom(ForecastPeriod::class.java)) {
      result.set("forecast", this.forecast as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(ForecastPeriod::class.java)) {
      result.set("forecast", this.forecast as Serializable)
    } else {
      throw UnsupportedOperationException(ForecastPeriod::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    if (Parcelable::class.java.isAssignableFrom(ForecastCity::class.java)) {
      result.set("city", this.city as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(ForecastCity::class.java)) {
      result.set("city", this.city as Serializable)
    } else {
      throw UnsupportedOperationException(ForecastCity::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): ForecastDetailActivityFragmentArgs {
      bundle.setClassLoader(ForecastDetailActivityFragmentArgs::class.java.classLoader)
      val __forecast : ForecastPeriod?
      if (bundle.containsKey("forecast")) {
        if (Parcelable::class.java.isAssignableFrom(ForecastPeriod::class.java) ||
            Serializable::class.java.isAssignableFrom(ForecastPeriod::class.java)) {
          __forecast = bundle.get("forecast") as ForecastPeriod?
        } else {
          throw UnsupportedOperationException(ForecastPeriod::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__forecast == null) {
          throw IllegalArgumentException("Argument \"forecast\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"forecast\" is missing and does not have an android:defaultValue")
      }
      val __city : ForecastCity?
      if (bundle.containsKey("city")) {
        if (Parcelable::class.java.isAssignableFrom(ForecastCity::class.java) ||
            Serializable::class.java.isAssignableFrom(ForecastCity::class.java)) {
          __city = bundle.get("city") as ForecastCity?
        } else {
          throw UnsupportedOperationException(ForecastCity::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__city == null) {
          throw IllegalArgumentException("Argument \"city\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"city\" is missing and does not have an android:defaultValue")
      }
      return ForecastDetailActivityFragmentArgs(__forecast, __city)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        ForecastDetailActivityFragmentArgs {
      val __forecast : ForecastPeriod?
      if (savedStateHandle.contains("forecast")) {
        if (Parcelable::class.java.isAssignableFrom(ForecastPeriod::class.java) ||
            Serializable::class.java.isAssignableFrom(ForecastPeriod::class.java)) {
          __forecast = savedStateHandle.get<ForecastPeriod?>("forecast")
        } else {
          throw UnsupportedOperationException(ForecastPeriod::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__forecast == null) {
          throw IllegalArgumentException("Argument \"forecast\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"forecast\" is missing and does not have an android:defaultValue")
      }
      val __city : ForecastCity?
      if (savedStateHandle.contains("city")) {
        if (Parcelable::class.java.isAssignableFrom(ForecastCity::class.java) ||
            Serializable::class.java.isAssignableFrom(ForecastCity::class.java)) {
          __city = savedStateHandle.get<ForecastCity?>("city")
        } else {
          throw UnsupportedOperationException(ForecastCity::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__city == null) {
          throw IllegalArgumentException("Argument \"city\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"city\" is missing and does not have an android:defaultValue")
      }
      return ForecastDetailActivityFragmentArgs(__forecast, __city)
    }
  }
}
