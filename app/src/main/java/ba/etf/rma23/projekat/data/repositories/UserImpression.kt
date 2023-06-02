package ba.etf.rma23.projekat.data.repositories

abstract class UserImpression(
    open val username: String? = "",
    open val timestamp: Long = 0,
)
