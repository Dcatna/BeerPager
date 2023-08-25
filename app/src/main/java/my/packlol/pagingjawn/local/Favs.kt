package my.packlol.pagingjawn.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import my.packlol.pagingjawn.domain.Beer
import org.jetbrains.annotations.NotNull

@Entity
data class Favs(
    @PrimaryKey
    val id : Int,
    val name : String,
    val tagline : String,
    val description : String,
    val firstBrewed : String,
    val imageUrl : String? = "",
    var saved : Boolean

)
