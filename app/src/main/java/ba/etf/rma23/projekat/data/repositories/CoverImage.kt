package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class CoverImage (
    @SerializedName("url") var url: String?
)