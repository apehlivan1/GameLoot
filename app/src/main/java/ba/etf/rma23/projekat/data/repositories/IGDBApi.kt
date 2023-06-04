package ba.etf.rma23.projekat.data.repositories

import retrofit2.Response
import retrofit2.http.*

interface IGDBApi {
    @Headers(
        value = [
            "Client-ID: n27db5sy8zyg0p1vrrhujjvi21copu",
            "Authorization: Bearer 2o3ra0ycb37ugpkgahn9lwlljpc6xv",
        ]
    )
    @GET("games/")
    suspend fun getGames(
        @Query("search") searchText: String,
        @Query("fields") name: String = "id,name,platforms.name,rating,first_release_date,age_ratings.rating,cover.url,genres.name,summary,involved_companies.company.name, involved_companies.developer, involved_companies.publisher"
    ): Response<List<Game>>
}

