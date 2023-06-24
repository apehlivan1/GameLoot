package ba.etf.rma23.projekat.data.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "gamereview")
data class GameReview (
    @ColumnInfo(name = "rating") @SerializedName("rating") var rating: Int?,
    @ColumnInfo(name = "review") @SerializedName("review") var review: String?,
    @ColumnInfo(name = "IGDBid") var igdb_id: Int,
    @ColumnInfo(name = "online") var online: Boolean = false,
    @ColumnInfo(name = "timestamp") var timestamp: String?,
    @ColumnInfo(name = "student") var student: String,
    @PrimaryKey(autoGenerate = true) @SerializedName("id") var id: Int = 0
)