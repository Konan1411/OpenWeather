package com.example.weather.ui

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.weather.R

public class SettingsFragmentDirections private constructor() {
  public companion object {
    public fun actionSettingsFragmentToChangePasswordFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_settingsFragment_to_ChangePasswordFragment)
  }
}
