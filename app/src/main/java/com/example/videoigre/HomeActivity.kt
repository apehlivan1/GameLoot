package com.example.videoigre

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var navController_right: NavController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)

        navController.graph.setStartDestination(R.id.homeItem)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            navView.visibility = View.GONE
            onConfigurationChanged(Configuration())
        }
        else {
            navView.visibility = View.VISIBLE
        }

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeItem -> {
                    NavHostFragment.findNavController(navHostFragment).navigate(R.id.homeItem)
                }
                R.id.gameDetailsItem -> {
                    NavHostFragment.findNavController(navHostFragment).navigate(R.id.gameDetailsItem, Bundle().apply { putString("selected_game", GameDetailsFragment.lastOpenedGame) })
                }
            }
            true
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.menu.findItem(R.id.homeItem).isEnabled = destination.id != R.id.homeItem
            navView.menu.findItem(R.id.gameDetailsItem).isEnabled =
                destination.id != R.id.gameDetailsItem
            if (GameDetailsFragment.lastOpenedGame == "") navView.menu.findItem(R.id.gameDetailsItem).isEnabled = false
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val navhostfragmentRight = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_right) as NavHostFragment
        navController_right = navhostfragmentRight.findNavController()
        navController_right.graph.setStartDestination(R.id.gameDetailsItem)
        val gamesList = GameData.getAll()
        val gameToShow: String = if (GameDetailsFragment.lastOpenedGame == "") gamesList.first().title
        else GameDetailsFragment.lastOpenedGame
        NavHostFragment.findNavController(navhostfragmentRight).navigate(R.id.gameDetailsItem, Bundle().apply { putString("selected_game", gameToShow) })
    }
}
