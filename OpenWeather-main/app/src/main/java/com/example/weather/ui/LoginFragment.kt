// LoginFragment.kt
package com.example.weather.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.data.AppDatabase
import com.example.weather.data.UserDao
import com.example.weather.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class LoginFragment : Fragment() {

    private lateinit var userDao: UserDao
    private lateinit var sessionManager: SessionManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_activity, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        val usernameEditText = view.findViewById<EditText>(R.id.username)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val loginButton = view.findViewById<Button>(R.id.login_button)
        val signUpButton = view.findViewById<Button>(R.id.sign_up_button)

        userDao = AppDatabase.getInstance(requireContext()).userDao() // Initialisation de userDao ici

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            lifecycleScope.launch {
                // Utilisation de userDao ici
                val user = withContext(Dispatchers.IO) {
                    userDao.getUser(username, password)
                }

                if (user != null) {
                    val sharedPref = activity?.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    sessionManager.loginUser(user.id ?: -1)
                    with(sharedPref?.edit()) {
                        this?.putInt(getString(R.string.pref_user_id), user.id ?: -1)
                        this?.putString(getString(R.string.pref_username), user.username)
                        this?.apply()
                    }
                    Toast.makeText(requireContext(), "Login successfully", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).recreate()
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)

                } else {
                    Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }
}
