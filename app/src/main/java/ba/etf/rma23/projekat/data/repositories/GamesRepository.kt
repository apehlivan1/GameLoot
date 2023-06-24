package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getAgeRequirementForRating
import ba.etf.rma23.projekat.data.repositories.AccountState.account
import kotlinx.coroutines.*

object GamesRepository {

    suspend fun getGamesByName(name: String): List<Game> = withContext(Dispatchers.IO) {
        val response = IGDBApiConfig.retrofit.getGames(name)
        val games = response.body() ?: emptyList()
        for (game in games) {
            game.initialize()
        }
        return@withContext games
    }

    suspend fun getGamesSafe(name: String): List<Game> {
        val age = account.age
        val allGames = getGamesByName(name)
        val safeGames = allGames.filter { game ->
            if (game.ratingsList.isNullOrEmpty()) return@filter false
            val ageRequirement = getAgeRequirementForRating(game.ratingsList!!)
            ageRequirement != null && age >= ageRequirement
        }
        return safeGames
    }

    suspend fun getGameById(id: Int): Game?  = withContext(Dispatchers.IO) {
        val response = IGDBApiConfig.retrofit.getGameById(id)
        val game = response.body()?.get(0)
        game?.initialize()
        return@withContext game
    }

    suspend fun sortGames(): List<Game> {
        var favoriteGames = AccountGamesRepository.getSavedGames()
        var gamesFromFragment = getGamesByName("")
        gamesFromFragment = gamesFromFragment.sortedBy { it.title }
        favoriteGames = favoriteGames.sortedBy { it.title }

        gamesFromFragment = gamesFromFragment.filterNot { gameFromFragment ->
            favoriteGames.any { favoriteGame -> favoriteGame.id == gameFromFragment.id }
        }
        return favoriteGames + gamesFromFragment
    }

}