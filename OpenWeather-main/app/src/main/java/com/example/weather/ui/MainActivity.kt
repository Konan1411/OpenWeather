/**
 * The main activity of the application, manages the navigation and sidebar.
 * Set up ActionBar and DrawerLayout.
 * Dynamically update the sidebar menu items by observing the ViewModel's list of cities.
 * Provides a dialog box and processing method for deleting a city.
 */

package com.example.weather.ui

import android.app.AlertDialog
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.BuildConfig
import com.example.weather.R
import com.example.weather.data.MyCities
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

const val OPENWEATHER_APPID = BuildConfig.OPENWEATHER_API_KEY

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfig: AppBarConfiguration

    private val viewModel: FiveDayForecastViewModel by viewModels()
    private val myCitiesViewModel: MyCitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        val navController = navHostFragment.navController
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfig)

        findViewById<NavigationView>(R.id.nav_view)?.setupWithNavController(navController)

        addCitiesToDrawer()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    private fun addCitiesToDrawer() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        val navView: NavigationView = findViewById(R.id.nav_view)
        val citiesSubMenu = navView.menu.findItem(R.id.submenu_cities)?.subMenu

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        myCitiesViewModel.myCities_List.observe(this) { cityList ->
            citiesSubMenu?.clear()

            for ((index, decity) in cityList.withIndex()) {
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

                    myCitiesViewModel.addMyCities(MyCities(0,decity.city, 1, System.currentTimeMillis())) // a changer user

                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.forecast_list)

                    true
                }

                // 设置长按事件
                menuItem?.setOnMenuItemClickListener {
                    showCityOptionsDialog(menuItem.title.toString(), index, cityList)
                    true
                }
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

        myCitiesViewModel.addMyCities(MyCities(0, cityName, 1, System.currentTimeMillis())) // a changer user

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

}