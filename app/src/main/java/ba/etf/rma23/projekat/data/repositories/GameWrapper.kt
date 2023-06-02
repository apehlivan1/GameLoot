package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GameWrapper (
    @SerializedName("fav_game") var favGame: FavouriteGame?
)

data class FavouriteGame(
    @SerializedName("igdb_id") var igdbId: Long,
    @SerializedName("name") var name: String?
)