package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName
import java.util.*

data class Game(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var title: String,
    @SerializedName("platforms") var platform: List<Platform>?,
    @SerializedName("first_release_date") var releaseDate: String?,//release_dates.human
    @SerializedName("rating") var rating: Double?,
    @SerializedName("cover") var coverImage: CoverImage?,
    @SerializedName("age_ratings") var esrbRating: List<Int>?,
    //@SerializedName("developers") var developer: List<InvolvedCompany>?,
    //@SerializedName("publishers") var publisher: List<InvolvedCompany>?,
    @SerializedName("genres") var genre: List<Genre>?,
    @SerializedName("summary") var description: String?,
    @SerializedName("user_impressions") var userImpressions: List<UserImpression>?
)