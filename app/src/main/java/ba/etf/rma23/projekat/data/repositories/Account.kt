package ba.etf.rma23.projekat.data.repositories

data class Account(
    var student: String = "apehlivan1",
    var acHash: String = "f478ee21-a49e-44f3-a363-959d75096eb3",
    var age: Int = -1,
    var savedGames: List<Game>? = null
)

object AccountState {
    val account = Account()
}