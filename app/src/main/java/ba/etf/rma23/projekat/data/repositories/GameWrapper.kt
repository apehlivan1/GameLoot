package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GameWrapper (
    @SerializedName("game") var favGame: FavouriteGame?
)

data class FavouriteGame(
    @SerializedName("igdb_id") var igdbId: Int,
    @SerializedName("name") var name: String?
)

data class DeleteGameResponse(
    @SerializedName("success") val success: String
)