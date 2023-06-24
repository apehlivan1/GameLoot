package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ba.etf.rma23.projekat.data.repositories.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var navController_right: NavController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)

        navController.graph.setStartDestination(R.id.homeItem)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            navView.visibility = View.GONE
            onConfigurationChanged(Configuration())
        } else {
            navView.visibility = View.VISIBLE
        }

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeItem -> {
                    NavHostFragment.findNavController(navHostFragment).navigate(R.id.homeItem)
                }
                R.id.gameDetailsItem -> {
                    NavHostFragment.findNavController(navHostFragment)
                        .navigate(R.id.gameDetailsItem, Bundle().apply {
                            putInt("selected_game_id", GameDetailsFragment.lastOpenedGame)
                        })
                }
            }
            true
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.menu.findItem(R.id.homeItem).isEnabled = destination.id != R.id.homeItem
            navView.menu.findItem(R.id.gameDetailsItem).isEnabled =
                destination.id != R.id.gameDetailsItem
            if (GameDetailsFragment.lastOpenedGame == -1) navView.menu.findItem(R.id.gameDetailsItem).isEnabled =
                false
        }
        AccountGamesRepository.setHash("f478ee21-a49e-44f3-a363-959d75096eb3")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val navhostfragmentRight = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_right) as NavHostFragment
        navController_right = navhostfragmentRight.findNavController()
        navController_right.graph.setStartDestination(R.id.gameDetailsItem)

        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            try {
                val gamesList = search()
                val gameToShowId: Int = if (GameDetailsFragment.lastOpenedGame == -1) gamesList.first().id
                else GameDetailsFragment.lastOpenedGame
                NavHostFragment.findNavController(navhostfragmentRight)
                    .navigate(R.id.gameDetailsItem, Bundle().apply {
                        putInt("selected_game_id", gameToShowId)
                    })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun search(): List<Game> {
        return GamesRepository.getGamesByName("")
    }
}
