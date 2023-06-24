package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.R
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ba.etf.rma23.projekat.data.repositories.AccountState.account


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
    private lateinit var favouriteButton: AppCompatImageButton
    private lateinit var picture: ImageView
    companion object{
        var lastOpenedGame: Int = -1
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
        favouriteButton = view.findViewById(R.id.favourite_button)
        picture = view.findViewById(R.id.cover_imageview)

        recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        listAdapter = DetailsAdapter(arrayListOf())
        recycler.adapter = listAdapter

        val gameId: Int = arguments?.getInt("selected_game_id")
            ?: throw NoSuchElementException("Game not found")
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            try {
                game = GamesRepository.getGameById(gameId)!!
                populateDetails()
                getUserImpressions()
                lastOpenedGame = game.id
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        favouriteButton.setOnClickListener{
            saveOrRemove()
        }
        return view
    }

    private fun populateDetails() {
        if (::game.isInitialized) {
            title.text = game.title
            platform.text = game.platform
            releaseDate.text = game.releaseDate
            esrbRating.text = game.esrbRating
            developer.text = game.developer
            publisher.text = game.publisher
            genre.text = game.genre
            description.text = game.description
            val context: Context = picture.context
            val default = R.drawable.controller
            context.let {
                Glide.with(it)
                    .load("https:" + (game.coverImage))
                    .placeholder(default)
                    .error(default)
                    .fallback(default)
                    .into(picture)
            }
        }
    }

    private fun saveOrRemove() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            try {
                val savedGames = AccountGamesRepository.getSavedGames()
                val gameExists = savedGames.any { it.id == game.id }
                if (gameExists) {
                    val toast = Toast.makeText(context, "Removed from favourites!", Toast.LENGTH_SHORT)
                    toast.show()
                    AccountGamesRepository.removeGame(game.id)
                } else {
                    try {
                        Toast.makeText(context, "Added to favourites!", Toast.LENGTH_SHORT).show()
                        AccountGamesRepository.saveGame(game)
                    } catch (e: Exception) {
                        onError()
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                onError()
                e.printStackTrace()
            }
        }
    }

    private fun onError() {
        Toast.makeText(context, "Can't add to favourite!", Toast.LENGTH_SHORT).show()
    }

    private fun addReview() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            GameReviewsRepository.sendReview(context!!, GameReview(3, "testni komentar", game.id, true, "", account.student))
        }
    }

    private fun getUserImpressions() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val reviews = GameReviewsRepository.getReviewsForGame(game.id)
            val impressions = game.setUserImpressions(reviews)
            listAdapter.updateDetails(impressions)
        }
    }
}