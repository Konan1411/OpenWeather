
package com.example.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import android.graphics.Color
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText = view.findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val confirmPasswordEditText = view.findViewById<EditText>(R.id.confirmPasswordEditText)


        val signUpButton = view.findViewById<Button>(R.id.signUpButton)

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val errorTextView = view.findViewById<TextView>(R.id.errorTextView)


            if (password != confirmPassword) {
                val errorMessage = "Passwords do not match"
                errorTextView.text = errorMessage
                errorTextView.visibility = View.VISIBLE

                confirmPasswordEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.custom_red))

                return@setOnClickListener
            }
            Toast.makeText(requireContext(), "Account created successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }



    }

}
