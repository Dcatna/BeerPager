package my.packlol.pagingjawn.domain

class Beer(
    val id : Int,
    val name : String,
    val tagline : String,
    val description : String,
    val first_brewed : String,
    val image_url : String?
) {

    fun doesMatchSearchQuery(query: String) : Boolean{
        val matchingCombos = listOf(
            "$id"
        )
        return matchingCombos.any{
            it.contains(query, ignoreCase = true)
        }
    }
}