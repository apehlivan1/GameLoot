package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getAgeRequirementForRating
import kotlinx.coroutines.*

object GamesRepository {

    suspend fun getGamesByName(name: String): List<Game> {
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGames(name)
            return@withContext response.body() ?: emptyList()
        }
    }

    suspend fun getGamesSafe(name: String): List<Game> {
        val age = AccountGamesRepository.account.age
        val allGames = getGamesByName(name)
        val safeGames = allGames.filter { game ->
            if (game.esrbRating.isNullOrEmpty()) return@filter false
            val ageRequirement = getAgeRequirementForRating(game.esrbRating!!)
            ageRequirement != null && age <= ageRequirement
        }
        return safeGames
    }

    fun getGameById(id: Long, name: String): Game {
        var game: Game? = null
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val gamesWithSameName = getGamesByName(name)
            game = gamesWithSameName.find { it.id == id }!!
        }
        return game?: throw NoSuchElementException("Game not found")
    }
/*
    fun sortGames(): List<Game> {

    }

*/
}