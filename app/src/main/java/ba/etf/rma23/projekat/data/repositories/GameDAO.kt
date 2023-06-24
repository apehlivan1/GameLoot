package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameDAO {
    @Query("SELECT * FROM gamereview WHERE online = false")
    suspend fun getAll(): List<GameReview>
    @Insert
    suspend fun insertAll(vararg game: GameReview)
    @Update
    suspend fun updateReviews(reviews: List<GameReview>)
}