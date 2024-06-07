package com.example.weather.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weather.R

class ChangePasswordFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        val newPasswordEditText = view.findViewById<EditText>(R.id.edit_new_password)
        val confirmPasswordEditText = view.findViewById<EditText>(R.id.edit_confirm_password)
        val changePasswordButton = view.findViewById<Button>(R.id.button_change_password)

        changePasswordButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (newPassword == confirmPassword) {
                val sharedPref = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
                val username = sharedPref.getString("username", "no") ?: "no"

                userViewModel.updatePassword(username, newPassword)

                Toast.makeText(requireContext(), "Password changed successfully", Toast.LENGTH_SHORT).show()

                findNavController().popBackStack()
            } else {
                confirmPasswordEditText.error = "Passwords do not match"
            }
        }
    }
}
