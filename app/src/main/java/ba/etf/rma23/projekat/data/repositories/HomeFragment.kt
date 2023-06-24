package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.MainActivity
import ba.etf.rma23.projekat.R
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGamesByName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var gameList: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private lateinit var searchText: EditText
    private lateinit var searchButton: AppCompatImageButton
    private lateinit var favouritesButton: AppCompatImageButton
    private lateinit var sortButton: AppCompatImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        searchText = view.findViewById(R.id.search_query_edittext)
        searchButton = view.findViewById(R.id.search_button)
        favouritesButton = view.findViewById(R.id.favourite_button)
        sortButton = view.findViewById(R.id.sort_button)
        gameList = view.findViewById(R.id.game_list)
        gameList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        gameListAdapter = GameListAdapter(arrayListOf()) { games -> showGameDetails(games) }
        gameList.adapter = gameListAdapter

        if (isConnectedToNetwork()) {
            search("")
        } else {
            Toast.makeText(context, "No internet connection available", Toast.LENGTH_SHORT).show()
        }

        searchButton.setOnClickListener{
            if (isConnectedToNetwork()) {
                search(searchText.text.toString())
                Toast.makeText(context, "Search start", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No internet connection available", Toast.LENGTH_SHORT).show()
            }
        }
        favouritesButton.setOnClickListener{
            val toast = Toast.makeText(context, "Showing favourite games...", Toast.LENGTH_SHORT)
            toast.show()
            if (isConnectedToNetwork()) {
                showSavedGames()
            } else {
                Toast.makeText(context, "No internet connection available", Toast.LENGTH_SHORT).show()
            }
        }
        sortButton.setOnClickListener{
            val toast = Toast.makeText(context, "Sorting...", Toast.LENGTH_SHORT)
            toast.show()

            if (isConnectedToNetwork()) {
                sortGames()
            } else {
                Toast.makeText(context, "No internet connection available", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun search(query: String) {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            try {
                val result = getGamesByName(query)
                gameListAdapter.updateGames(result)
            } catch (e: Exception) {
                onError()
                e.printStackTrace()
            }
        }
    }

    private fun showSavedGames() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            try {
                val savedGames = AccountGamesRepository.getSavedGames()
                gameListAdapter.updateGames(savedGames)
            } catch (e: Exception) {
                onError()
                e.printStackTrace()
            }
        }
    }

    private fun sortGames() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            try {
                val sortedGames = GamesRepository.sortGames()
                gameListAdapter.updateGames(sortedGames)
            } catch (e: Exception) {
                onError()
                e.printStackTrace()
            }
        }
    }

    private fun onError() {
        val toast = Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
        toast.show()
    }
    private fun showGameDetails(game: Game) {
        GameDetailsFragment.lastOpenedGame = game.id
        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE)
            findNavController().navigate(R.id.gameDetailsItem, Bundle().apply {
                putInt("selected_game_id", game.id)
            })
        else
            MainActivity.navController_right.navigate(R.id.gameDetailsItem, Bundle().apply {
                putInt("selected_game_id", game.id)
            })
    }

    private fun isConnectedToNetwork(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}