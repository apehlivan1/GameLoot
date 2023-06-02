package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class InvolvedCompany(
    @SerializedName("company") var company: Company?,
    @SerializedName("developer") var developer: Boolean?,
    @SerializedName("publisher") var publisher: Boolean?
)
