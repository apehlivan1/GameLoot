package com.example.videoigre

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GameDetailsFragment : Fragment() {
    private lateinit var game: Game
    private lateinit var title: TextView
    private lateinit var platform: TextView
    private lateinit var releaseDate: TextView
    private lateinit var esrbRating: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView
    private lateinit var recycler: RecyclerView
    private lateinit var listAdapter: DetailsAdapter
    companion object{
        var lastOpenedGame: String = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)
        title = view.findViewById(R.id.item_title_textview)
        platform = view.findViewById(R.id.platform_textview)
        releaseDate = view.findViewById(R.id.release_date_textview)
        esrbRating = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)
        recycler = view.findViewById(R.id.recyclerView)


        game = getGameByTitle(arguments?.getString("selected_game"))
        lastOpenedGame = game.title


        populateDetails()
        recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        listAdapter = DetailsAdapter(arrayListOf())
        recycler.adapter = listAdapter
        listAdapter.updateDetails(game.userImpressions)
        return view
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

    private fun getGameByTitle(name: String?): Game {
        val games: ArrayList<Game> = arrayListOf()
        games.addAll(GameData.getAll())
        val game = games.find { game -> name == game.title }
        return game?:Game("Test","Test","Test",0.0,"Test","Test", "Test", "Test", "Test", "Test", emptyList())
    }
}