package my.packlol.pagingjawn.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BeerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BeerDatabse : RoomDatabase(){

    abstract val dao : BeerDao
}