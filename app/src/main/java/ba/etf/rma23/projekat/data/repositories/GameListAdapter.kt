package ba.etf.rma23.projekat.data.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.R

class GameListAdapter (
    private var gameList: List<Game>,
    private val onItemClicked: (game: Game) -> Unit
) : RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int = gameList.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.title.text = gameList[position].title
        holder.releaseDate.text = gameList[position].releaseDate

        val platformNames = gameList[position].platformsList?.map { it.name }
        val platformsString = platformNames?.joinToString(", ")
        holder.platform.text = platformsString

        holder.rating.text = gameList[position].rating.toString()
        holder.itemView.setOnClickListener{ onItemClicked(gameList[position]) }
    }

    fun updateGames(games: List<Game>?) {
        if (games != null) {
            this.gameList = games
            notifyDataSetChanged()
        }
    }

    inner class GameViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.item_title_textview)
        val releaseDate : TextView = itemView.findViewById(R.id.release_date)
        val platform : TextView = itemView.findViewById(R.id.game_platform_textview)
        val rating : TextView = itemView.findViewById(R.id.game_rating_textview)
    }
}