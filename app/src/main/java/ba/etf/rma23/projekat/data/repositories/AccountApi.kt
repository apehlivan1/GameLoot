package ba.etf.rma23.projekat.data.repositories

import retrofit2.Response
import retrofit2.http.*

interface AccountApi {
    @GET("/account/{aid}/games")
    fun getAccountGames(@Path("aid") accountId: String): Response<List<Game>>

    @POST("/account/{aid}/game")
    fun createGame(
        @Path("aid") accountId: String,
        @Body wrapper: GameWrapper
    )

    @DELETE("/account/{aid}/game")
    fun deleteAllGames(@Path("aid") accountId: String)

    @DELETE("/account/{aid}/game/{gid}")
    fun deleteGame(
        @Path("aid") accountId: String,
        @Path("gid") gameId: Int
    ): Response<String>
}