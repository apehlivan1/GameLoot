package com.example.videoigre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videoigre.GameData.Companion.getAll

class GameDetailsActivity : AppCompatActivity() {
    private lateinit var game: Game
    private lateinit var title: TextView
    private lateinit var platform: TextView
    private lateinit var releaseDate: TextView
    private lateinit var esrbRating: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView
    private lateinit var homeButton: Button
    private lateinit var detailsButton: Button
    private lateinit var recycler: RecyclerView
    private lateinit var impressionsListAdapter: DetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        title = findViewById(R.id.game_title_textview)
        platform = findViewById(R.id.platform_textview)
        releaseDate = findViewById(R.id.release_date_textview)
        esrbRating = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre = findViewById(R.id.genre_textview)
        description = findViewById(R.id.description_textview)
        homeButton = findViewById(R.id.home_button)
        detailsButton = findViewById(R.id.details_button)
        recycler = findViewById(R.id.recyclerView)


        val extras = intent.extras
        if (extras != null) {
            game = getGameByTitle(extras.getString("game_title", ""))
            populateDetails()
        }
        else {
            finish()
        }

        recycler.layoutManager = LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false)
        impressionsListAdapter = DetailsAdapter(arrayListOf())
        recycler.adapter = impressionsListAdapter
        impressionsListAdapter.updateDetails(game.userImpressions)

        detailsButton.isEnabled = false

        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("game_title", game.title)
            }
            startActivity(intent)
        }
    }

    private fun populateDetails() {
        title.text = game.title
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esrbRating.text = game.esrbRating
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description
    }

    private fun getGameByTitle(name: String): Game {
        val games: ArrayList<Game> = arrayListOf()
        games.addAll(getAll())
        val game = games.find { game -> name == game.title }
        return game?:Game("Test","Test","Test",0.0,"Test","Test", "Test", "Test", "Test", "Test", emptyList())
    }
}