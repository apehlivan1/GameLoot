package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GameReviewsRepository {

    //vraća listu review-ova iz baze koji nisu poslani na web servis
    suspend fun getOfflineReviews(context: Context):List<GameReview> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            return@withContext db.gameDao().getAll()
        }
    }

    // vraća broj review-ova koji su uspješno poslani na web servis, a koji ranije nisu bili spašeni
    suspend fun sendOfflineReviews(context: Context):Int {
        val offlineReviws = getOfflineReviews(context)
        var counter = 0
        val updatedReviews = mutableListOf<GameReview>()
        for (gameReview in offlineReviws) {
            if (gameReview.rating in 0..5 && !gameReview.online) {
                val reviewRequest = GameReviewRequest(gameReview.review, gameReview.rating)
                AccountApiConfig.retrofit.postReview(AccountState.account.acHash, gameReview.igdb_id, reviewRequest)
                counter++
                gameReview.online = true
                updatedReviews.add(gameReview)
            }
        }
        if (updatedReviews.isNotEmpty()) {
            updateOnlineStatus(context, updatedReviews)
        }
        return counter
    }

    /**
     * update-uje polje online u bazi
     */
    private suspend fun updateOnlineStatus(context: Context, updatedReviews: List<GameReview>) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            db.gameDao().updateReviews(updatedReviews)
        }
    }

    @Suppress("DEPRECATION")
    private fun isConnectedToNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    /**
     * šalje review na web servis, vraća true ako je uspješno poslao.
     * Ako slanje nije uspješno, kopiju review-a snimite u bazu i vratite false.
     * Da bi review prošao igra mora biti dodana u listu omiljenih.
     */
    suspend fun sendReview(context: Context, gameReview: GameReview):Boolean {
        if (gameReview.rating !in 0..5 || !isConnectedToNetwork(context)) {
            return !saveReviewToDB(context, gameReview)
        } else {
            val savedGames = AccountGamesRepository.getSavedGames()
            val gameIdExists = savedGames.any { it.id == gameReview.igdb_id }
            if (!gameIdExists) {
                val game = GamesRepository.getGameById(gameReview.igdb_id)
                if (game != null) AccountGamesRepository.saveGame(game)
            }
            val reviewRequest = GameReviewRequest(gameReview.review, gameReview.rating)
            AccountApiConfig.retrofit.postReview(AccountState.account.acHash, gameReview.igdb_id, reviewRequest)
            saveReviewToDB(context, gameReview)
        }
        return true
    }

    /**
     * spasava review lokalno (u bazu)
     */
    suspend fun saveReviewToDB(context: Context, gameReview: GameReview):Boolean {
        return withContext(Dispatchers.IO) {
            try{
                val db = AppDatabase.getInstance(context)
                db.gameDao().insertAll(gameReview)
                return@withContext true
            }
            catch(error:Exception){
                return@withContext false
            }
        }
    }

    /**
     * vraća listu review-ova sa web servisa za igru sa zadanim igdb id-em
     */
    suspend fun getReviewsForGame(igdb_id:Int):List<GameReview> {
        return withContext(Dispatchers.IO) {
            val response = AccountApiConfig.retrofit.getReview(igdb_id)
            return@withContext response.body() ?: emptyList<GameReview>()
        }
    }
}