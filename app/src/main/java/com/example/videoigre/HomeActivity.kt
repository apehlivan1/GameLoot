package com.example.videoigre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videoigre.GameData.Companion.getAll


class HomeActivity : AppCompatActivity() {

    private lateinit var gameList: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private lateinit var searchText: EditText
    private lateinit var homeButton: Button
    private lateinit var detailsButton: Button
    private var gamesList = getAll()

    private lateinit var selectedGame: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        searchText = findViewById(R.id.search_query_edittext)
        gameList = findViewById(R.id.game_list)
        homeButton = findViewById(R.id.home_button)
        detailsButton = findViewById(R.id.details_button)

        gameList.layoutManager = LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false)
        gameListAdapter = GameListAdapter(arrayListOf()) { games -> showGameDetails(games) }
        gameList.adapter = gameListAdapter
        gameListAdapter.updateGames(gamesList)

        homeButton.isEnabled = false

        val extras = intent.extras
        if (extras != null) {
            selectedGame = getGameByTitle(extras.getString("game_title", ""))
        }
        else detailsButton.isEnabled = false
        detailsButton.setOnClickListener {
            // Check if a game has been selected
            selectedGame.let { game ->
                // Start the GameDetailsActivity with the selected game
                val intent = Intent(this, GameDetailsActivity::class.java).apply {
                    putExtra("game_title", game.title)
                }
                startActivity(intent)
            }
        }
    }

    private fun showGameDetails(game: Game) {
        detailsButton.isEnabled = true
        selectedGame = game
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game_title", game.title)
        }
        startActivity(intent)
    }
        private fun getGameByTitle(name: String): Game {
            val games: ArrayList<Game> = arrayListOf()
            games.addAll(getAll())
            val game = games.find { game -> name == game.title }
            return game?:Game("Test","Test","Test",0.0,"Test","Test", "Test", "Test", "Test", "Test", emptyList())
        }
}
