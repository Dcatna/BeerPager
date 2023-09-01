package my.packlol.pagingjawn.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Query("SELECT * FROM beerentity")
    suspend fun observeAll() : List<BeerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearch(beer : BeerEntity)

    @Delete
    suspend fun deleteBeer(beer: BeerEntity)
}

