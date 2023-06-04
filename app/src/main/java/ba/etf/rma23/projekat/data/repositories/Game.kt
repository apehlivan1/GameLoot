package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName
import java.util.*

data class Game (
    @SerializedName("id") var id: Int,
    @SerializedName("name") var title: String,
    var platform: String?,
    @SerializedName("first_release_date") var releaseDate: String?,
    @SerializedName("rating") var rating: Double?,
    var coverImage: String?,
    var esrbRating: String?,
    var developer: String?,
    var publisher: String?,
    var genre: String?,
    @SerializedName("summary") var description: String?,
    @SerializedName("user_impressions") var userImpressions: List<UserImpression>?,
    @SerializedName("platforms") var platformsList: List<Platform>? = null,
    @SerializedName("cover") var coverImageObject: CoverImage? = null,
    @SerializedName("age_ratings") var ratingsList: List<AgeRating>? = null,
    @SerializedName("involved_companies") var company: List<InvolvedCompany>? = null,
    @SerializedName("genres") var genreList: List<Genre>? = null
) {
    fun initialize() {
        platform = platformsList?.joinToString(", ") { it.name ?: "" } ?: ""
        coverImage = coverImageObject?.url ?: ""
        esrbRating = ratingsList?.joinToString(", ") { getRatingName(it.rating).toString() } ?: ""
        genre = genreList?.joinToString(", ") { it.name ?: "" } ?: ""
        releaseDate = releaseDate?.let {
            if (it.isNotEmpty()) {
                java.time.format.DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(it.toLong()))
            } else {
                null
            }
        }
        releaseDate = releaseDate?.substring(0, 10)

        val developers = mutableListOf<String>()
        val publishers = mutableListOf<String>()
        company?.forEach { involvedCompany ->
            if (involvedCompany.developer == true) {
                involvedCompany.company?.name?.let { developers.add(it) }
            }
            if (involvedCompany.publisher == true) {
                involvedCompany.company?.name?.let { publishers.add(it) }
            }
        }
        developer = developers.joinToString(", ")
        publisher = publishers.joinToString(", ")
    }

    private fun getRatingName(ratingValue: Int): String? {
        val ratingCategory = if (ratingValue in 1..5) "PEGI" else if (ratingValue in 6..12) "ESRB" else ""
        val rating = Rating.values().find { it.value == ratingValue } ?: return null
        return if (ratingCategory.isNotEmpty()) "$ratingCategory ${rating.name}" else rating.name
    }
}

