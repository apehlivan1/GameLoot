package ba.etf.rma23.projekat.data.repositories

import retrofit2.Response
import retrofit2.http.*

interface AccountApi {
    @GET("account/{aid}/games")
    suspend fun getAccountGames(@Path("aid") accountId: String): Response<List<FavouriteGame>>

    @POST("account/{aid}/game/")
    suspend fun saveGame(
        @Path("aid") accountId: String,
        @Body wrapper: GameWrapper
    )

    @DELETE("account/{aid}/game")
    suspend fun deleteAllGames(@Path("aid") accountId: String)

    @DELETE("account/{aid}/game/{gid}")
    suspend fun deleteGame(
        @Path("aid") accountId: String,
        @Path("gid") gameId: Int
    ): Response<DeleteGameResponse>

    @GET("/game/{gid}/gamereviews")
    suspend fun getReview(
        @Path("gid") igdb_id: Int
    ): Response<List<GameReview>>

    @POST("/account/{aid}/game/{gid}/gamereview")
    suspend fun postReview(
        @Path("aid") accountId: String,
        @Path("gid") gameId: Int,
        @Body gameReview: GameReviewRequest
    )
}