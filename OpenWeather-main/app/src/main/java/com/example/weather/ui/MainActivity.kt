package com.example.weather.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.weather.BuildConfig
import com.example.weather.R
import com.example.weather.data.MyCities
import com.example.weather.util.SessionManager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

const val OPENWEATHER_APPID = BuildConfig.OPENWEATHER_API_KEY

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var sessionManager: SessionManager

    private val viewModel: FiveDayForecastViewModel by viewModels()
    private val myCitiesViewModel: MyCitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        val navController = navHostFragment.navController
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfig)

        findViewById<NavigationView>(R.id.nav_view)?.setupWithNavController(navController)
        addCitiesToDrawer()
        addLogoutToDrawer()
        addCityToDrawer()


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    private fun addCityToDrawer() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        val addCityItem = navView.menu.findItem(R.id.addCity) // Référence à l'élément "Add City" du menu
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)

        addCityItem.setOnMenuItemClickListener {
            if (sessionManager.isUserLoggedIn()) {
                openAddCityDialog()
            } else {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_login_logout)
                drawer.closeDrawers()
            }
            true
        }
    }


    private fun addLogoutToDrawer() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        val logoutItem = navView.menu.findItem(R.id.action_login_logout)
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)

        if(sessionManager.isUserLoggedIn()){
            logoutItem.title = getString(R.string.label_logout)
        }else{
            logoutItem.title = getString(R.string.label_login)
        }

        logoutItem.setOnMenuItemClickListener {
            if (sessionManager.isUserLoggedIn()) {
                showLogoutConfirmationDialog()
                findNavController(R.id.nav_host_fragment).navigate(R.id.main_nav_graph)
            }else{
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_login_logout)
                drawer.closeDrawers()
            }
            true
        }
    }


    private fun addCitiesToDrawer() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val citiesSubMenu = navView.menu.findItem(R.id.submenu_cities)?.subMenu
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val defaultCity = "Belfort, France"

        // Observer les changements de la session utilisateur pour mettre à jour le menu
        sessionManager.isUserLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                val userId = sessionManager.getUserId()
                myCitiesViewModel.setUserId(userId)

                myCitiesViewModel.myCities_List.observe(this) { cityList ->
                    citiesSubMenu?.clear()
                    if (cityList.isNotEmpty()) {
                        val firstCity = cityList.first().city
                        sharedPrefs.edit {
                            putString("city", firstCity)
                            apply()
                        }
                        viewModel.loadFiveDayForecast(
                            firstCity,
                            sharedPrefs.getString(
                                getString(R.string.pref_units_key),
                                getString(R.string.pref_units_default_value)
                            ),
                            OPENWEATHER_APPID
                        )

                        cityList.forEachIndexed { index, decity ->
                            val menuItem = citiesSubMenu?.add(0, index, 0, decity.city)
                            menuItem?.setOnMenuItemClickListener {
                                sharedPrefs.edit {
                                    putString("city", decity.city)
                                    apply()
                                }
                                viewModel.loadFiveDayForecast(
                                    decity.city,
                                    sharedPrefs.getString(
                                        getString(R.string.pref_units_key),
                                        getString(R.string.pref_units_default_value)
                                    ),
                                    OPENWEATHER_APPID
                                )
                                drawer.closeDrawers()
                                true
                            }
                            menuItem?.setOnMenuItemClickListener {
                                showCityOptionsDialog(menuItem.title.toString(), index, cityList)
                                true
                            }
                        }
                    } else {
                        myCitiesViewModel.addMyCities(MyCities(null, "Belfort, France", userId, System.currentTimeMillis()))
                    }
                }
            } else {
                // Clear citiesSubMenu and add default city if no session is open
                citiesSubMenu?.clear()
                citiesSubMenu?.add(0, 0, 0, defaultCity)
            }
        }
    }

    private fun showCityOptionsDialog(cityName: String, index: Int, cityList: List<MyCities>) {
        val options = arrayOf("Open", "Delete")
        AlertDialog.Builder(this)
            .setTitle(cityName)
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> openCity(cityName) // 处理打开城市的逻辑
                    1 -> showDeleteCityDialog(cityList[index]) // 处理删除城市的逻辑
                }
            }
            .show()
    }

    private fun openCity(cityName: String) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefs.edit {
            putString("city", cityName)
            apply()
        }

        viewModel.loadFiveDayForecast(
            cityName,
            sharedPrefs.getString(
                getString(R.string.pref_units_key),
                getString(R.string.pref_units_default_value)
            ),
            OPENWEATHER_APPID
        )
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer.closeDrawers()

        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.forecast_list)
    }

    private fun showDeleteCityDialog(city: MyCities) {
        AlertDialog.Builder(this)
            .setTitle("Delete City")
            .setMessage("Are you sure you want to delete ${city.city}?")
            .setPositiveButton("Yes") { _, _ ->
                myCitiesViewModel.removeMyCities(city)
                Snackbar.make(findViewById(R.id.drawer_layout), "City ${city.city} deleted", Snackbar.LENGTH_SHORT).show()
                addCitiesToDrawer() // 刷新侧边栏
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun showLogoutConfirmationDialog() {
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                // Clear session data
                sessionManager.logoutUser()
                recreate()
                drawer.closeDrawers()
            // Optionally, navigate to the login screen or perform any other action
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun openAddCityDialog() {
        val builder = AlertDialog.Builder(this)
        val input = EditText(this)
        input.hint = "City"
        builder.setView(input)
        builder.setTitle("Add a city")
            .setPositiveButton("Add") { dialog, _ ->
                val cityName = input.text.toString().trim()
                if (cityName.isNotBlank()) {
                    val cityList = myCitiesViewModel.myCities_List.value
                    if (cityList != null && cityList.any { it.city.equals(cityName, true) }) {
                        Toast.makeText(this, "This city already exists.", Toast.LENGTH_SHORT).show()
                    } else {
                        addCity(cityName)
                    }
                } else {
                    Toast.makeText(this, "City name does not match with known cities.", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun addCity(cityName: String) {
        val userId = sessionManager.getUserId()
        val currentTimeMillis = System.currentTimeMillis()
        val myCity = MyCities(null, cityName, userId, currentTimeMillis)
        myCitiesViewModel.addMyCities(myCity)
    }


}
