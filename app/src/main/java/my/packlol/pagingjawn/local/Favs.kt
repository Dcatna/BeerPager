package my.packlol.pagingjawn.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import my.packlol.pagingjawn.domain.Beer

@Entity
data class Favs(
    @ColumnInfo val beer : Beer,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
