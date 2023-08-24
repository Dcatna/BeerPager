package my.packlol.pagingjawn.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BeerEntity::class, Favs::class],
    version = 1,
    exportSchema = false
)
abstract class BeerDatabse : RoomDatabase(){

    abstract val favdao : FavoritesDao
    abstract val dao : BeerDao
}