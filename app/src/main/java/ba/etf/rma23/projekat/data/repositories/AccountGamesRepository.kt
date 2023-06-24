package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.data.repositories.AccountState.account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountGamesRepository {

    fun setHash(acHash:String):Boolean {
        account.acHash = acHash
        return true
    }

    fun getHash():String {
        return account.acHash
    }

    suspend fun getSavedGames(): List<Game> = withContext(Dispatchers.IO) {
        val savedGames = mutableListOf<Game>()
        val favGame = accountGames()

        for (game in favGame) {
            val savedGame = GamesRepository.getGameById(game.igdbId)
            if (savedGame != null) savedGames.add(savedGame)
        }
         savedGames
    }

    private suspend fun accountGames(): List<FavouriteGame> = withContext(Dispatchers.IO) {
        val response = AccountApiConfig.retrofit.getAccountGames(account.acHash)
        return@withContext response.body() ?: emptyList()
    }

    suspend fun saveGame(game: Game):Game {
        val gameFromIGDB = GamesRepository.getGameById(game.id)
        val gameToSave = FavouriteGame(game.id, game.title)
        return withContext(Dispatchers.IO) {
            AccountApiConfig.retrofit.saveGame(account.acHash, GameWrapper(gameToSave))
            return@withContext gameFromIGDB!!
        }
    }

    suspend fun removeGame(id: Int):Boolean {
        return withContext(Dispatchers.IO) {
            val response = AccountApiConfig.retrofit.deleteGame(account.acHash, id)
            return@withContext response.body()?.success == "Games deleted"
        }
    }

    suspend fun removeNonSafe():Boolean {
        val savedGames = getSavedGames()
        if (savedGames.isEmpty()) return false
        val safeGames = savedGames.filter { game ->
            if (game.ratingsList.isNullOrEmpty()) return@filter false
            val ageRequirement = getAgeRequirementForRating(game.ratingsList!!)
            ageRequirement != null && account.age >= ageRequirement
        }
        val gamesToRemove = savedGames - safeGames.toSet()
        gamesToRemove.forEach { game ->
            removeGame(game.id)
        }
        return true
    }

    fun getAgeRequirementForRating(ratings: List<AgeRating>): Int? {
        var rating = ratings.firstOrNull { it.rating in 1..5 }
        if (rating == null) rating = ratings.firstOrNull {it.rating in 6..12 }
        return when (rating?.rating) {
            in 1..5 -> {
                when (rating?.rating) {
                    1 -> 3
                    2 -> 7
                    3 -> 12
                    4 -> 16
                    5 -> 18
                    else -> null
                }
            }
            in 6..12 -> {
                when (rating?.rating) {
                    6 -> 3
                    7 -> 6
                    8 -> 10
                    9 -> 13
                    10 -> 17
                    11 -> 18
                    else -> null
                }
            }
            else -> null
        }
    }

    suspend fun getGamesContainingString(query:String):List<Game> {
        val games = getSavedGames()
        return games.filter { game ->
            game.title.contains(query, ignoreCase = true)
        }
    }

    fun setAge(age:Int):Boolean {
        if (age < 3 || age > 100) return false
        account.age = age
        return true
    }
}