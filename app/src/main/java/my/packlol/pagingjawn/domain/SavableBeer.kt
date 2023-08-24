package my.packlol.pagingjawn.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

data class SavableBeer(
    val id : Int,
    val name : String,
    val tagline : String,
    val description : String,
    val firstBrewed : String,
    val imageUrl : String? = "",
    val saved : Boolean = false
)
