package ba.etf.rma23.projekat.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountGamesRepository {

    val account = Account()

    fun setHash(acHash:String):Boolean {
        account.acHash = acHash
        return true
    }

    fun getHash():String {
        return account.acHash
    }

    suspend fun getSavedGames():List<Game> {
        return withContext(Dispatchers.IO) {
            val response = AccountApiConfig.retrofit.getAccountGames(account.acHash)
            return@withContext response.body() ?: emptyList()
        }
    }

    suspend fun saveGame(game: Game):Game {
        return withContext(Dispatchers.IO) {
            val gameToSave = FavouriteGame(game.id, game.title)
            AccountApiConfig.retrofit.createGame(account.acHash, GameWrapper(gameToSave))
            return@withContext game
        }
    }

    suspend fun removeGame(id: Int):Boolean {
        return withContext(Dispatchers.IO) {
            val response = AccountApiConfig.retrofit.deleteGame(account.acHash, id)
            return@withContext (response.body() == "Games deleted")
        }
    }

    suspend fun removeNonSafe():Boolean {
        val savedGames = getSavedGames()
        if (savedGames.isEmpty()) return false
        val safeGames = savedGames.filter { game ->
            if (game.esrbRating.isNullOrEmpty()) return@filter false
            val ageRequirement = getAgeRequirementForRating(game.esrbRating!!)
            ageRequirement != null && account.age >= ageRequirement
        }
        val gamesToRemove = savedGames - safeGames.toSet()
        gamesToRemove.forEach { game ->
            removeGame(game.id.toInt())
        }
        return true
    }

    fun getAgeRequirementForRating(esrb: List<Int>): Int? {
        //if rating category is PEGI
        val rating = esrb.firstOrNull { it in 1..5 }
        //else if rating category is ESRB
        if (rating != null) esrb.firstOrNull { it in 6..12}
        return when (rating) {
            1 -> 3
            2 -> 7
            3 -> 12
            4 -> 16
            5 -> 18
            7 -> 3
            8 -> 6
            9 -> 10
            10 -> 13
            11 -> 17
            12 -> 18
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