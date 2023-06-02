package ba.etf.rma23.projekat.data.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.R


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
    companion object{
        var lastOpenedGameId: Long = -1
        var lastOpenedGameName: String = ""
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


        val gameName: String? = arguments?.getString("selected_game_name")
        val gameId: Long? = arguments?.getLong("selected_game_id")
        if (gameId != null && gameName != null)
            game = GamesRepository.getGameById(gameId, gameName)
        populateDetails()
        lastOpenedGameId = game.id
        lastOpenedGameName = game.title

        return view
    }


    private fun populateDetails() {
        title.text = game.title

        val platformNames = game.platform?.map { it.name }
        val platformsString = platformNames?.joinToString(", ")
        platform.text = platformsString

        releaseDate.text = game.releaseDate

        val esrbRatings = game.esrbRating?.mapNotNull { getRatingName(it) }
        val esrbRatingsString = esrbRatings?.joinToString(", ")
        esrbRating.text = esrbRatingsString

        //developer.text = game.developer
        //publisher.text = game.publisher

        val genresNames = game.platform?.map { it.name }
        val genresString = genresNames?.joinToString(", ")
        genre.text = genresString

        description.text = game.description
    }

    private fun getRatingName(ratingValue: Int): String? {
        val ratingCategory = if (ratingValue in 1..5) "PEGI" else if (ratingValue in 6..12) "ESRB" else ""
        val rating = Rating.values().find { it.value == ratingValue } ?: return null
        return if (ratingCategory.isNotEmpty()) "$ratingCategory ${rating.name}" else rating.name
    }
}