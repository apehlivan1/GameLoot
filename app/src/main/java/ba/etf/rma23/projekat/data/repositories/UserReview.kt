package ba.etf.rma23.projekat.data.repositories

data class UserReview(
    override val username: String?,
    override val timestamp: Long,
    val review: String
): UserImpression()