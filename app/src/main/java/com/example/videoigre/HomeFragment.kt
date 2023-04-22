package com.example.videoigre

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videoigre.GameData.Companion.getAll

class HomeFragment : Fragment() {
    private lateinit var gameList: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private lateinit var searchText: EditText
    private var gamesList = getAll()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        searchText = view.findViewById(R.id.search_query_edittext)
        gameList = view.findViewById(R.id.game_list)
        gameList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        gameListAdapter = GameListAdapter(arrayListOf()) { games -> showGameDetails(games) }
        gameList.adapter = gameListAdapter
        gameListAdapter.updateGames(gamesList)
        return view
    }

    private fun showGameDetails(game: Game) {
        GameDetailsFragment.lastOpenedGame = game.title
        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE)
            findNavController().navigate(R.id.gameDetailsItem, Bundle().apply { putString("selected_game", game.title) })
        else
            HomeActivity.navController_right.navigate(R.id.gameDetailsItem, Bundle().apply { putString("selected_game", game.title) })
    }
}