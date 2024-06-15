package com.example.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.data.User
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val nameEditText = view.findViewById<EditText>(R.id.signName)
        val usernameEditText = view.findViewById<EditText>(R.id.signUsername)
        val passwordEditText = view.findViewById<EditText>(R.id.signPassword)
        val confirmPasswordEditText = view.findViewById<EditText>(R.id.signConfPassword)
        val signUpButton = view.findViewById<Button>(R.id.signButton)
        val errorTextView = view.findViewById<TextView>(R.id.errorView)

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password != confirmPassword) {
                    confirmPasswordEditText.error = "Passwords do not match"
                    return@setOnClickListener
                }

                // Utilisation de la coroutine pour ajouter l'utilisateur de manière asynchrone
                lifecycleScope.launch {
                    val existingUser = userViewModel.getUserByUsername(username)

                    if (existingUser == null) {
                        val user = User(null, username, password)
                        userViewModel.addUser(user)
                        Toast.makeText(requireContext(), "Account created successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    } else {
                        errorTextView.text = "Username is already taken"
                        errorTextView.visibility = View.VISIBLE
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
