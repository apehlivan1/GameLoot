package ba.etf.rma23.projekat.data.repositories

import android.content.res.Configuration
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
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGameById
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

        search("")

        searchButton.setOnClickListener{
            val toast = Toast.makeText(context, "Search start", Toast.LENGTH_SHORT)
            toast.show()
            search(searchText.text.toString())
        }
        favouritesButton.setOnClickListener{
            val toast = Toast.makeText(context, "Your favourite games...", Toast.LENGTH_SHORT)
            toast.show()
            showSavedGames()
        }
        sortButton.setOnClickListener{
            val toast = Toast.makeText(context, "Sorting...", Toast.LENGTH_SHORT)
            toast.show()
            sortGames()
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
        GameDetailsFragment.lastOpenedGameId = game.id
        GameDetailsFragment.lastOpenedGameName = game.title
        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE)
            findNavController().navigate(R.id.gameDetailsItem, Bundle().apply {
                putInt("selected_game_id", game.id)
                putString("selected_game_name", game.title)
            })
        else
            MainActivity.navController_right.navigate(R.id.gameDetailsItem, Bundle().apply {
                putInt("selected_game_id", game.id)
                putString("selected_game_name", game.title)
            })
    }
}