/**
 * Set the Fragment of the screen and use PreferenceFragmentCompat to display application settings.
 */

package com.example.weather.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.weather.R

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}