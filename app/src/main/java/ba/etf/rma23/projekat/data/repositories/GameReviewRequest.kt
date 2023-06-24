package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GameReviewRequest(
    @SerializedName("review") var review: String?,
    @SerializedName("rating") var rating: Int?
)
