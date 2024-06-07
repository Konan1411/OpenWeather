/**
 * Set the Fragment of the screen and use PreferenceFragmentCompat to display application settings.
 */

package com.example.weather.ui

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.example.weather.R
import com.example.weather.util.SessionManager

class SettingsFragment: PreferenceFragmentCompat() {

    private lateinit var sessionManager: SessionManager

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        sessionManager = SessionManager(requireContext())
        setPreferencesFromResource(R.xml.settings, rootKey)
        sessionManager.isUserLoggedIn.observe(this) { isLoggedIn ->
            val changePasswordCategory: PreferenceCategory? = findPreference("change_password_category")
            val changePassword: Preference? = findPreference("change_password_key")
            changePasswordCategory?.isVisible = isLoggedIn
            changePassword?.setOnPreferenceClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_ChangePasswordFragment)
                true
            }
        }
    }

}