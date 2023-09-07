package my.packlol.pagingjawn.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import my.packlol.pagingjawn.domain.SavableBeer

@Dao
interface SearchDao {

    @Query("SELECT * FROM beerentity")
    fun observeAll() : List<BeerEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearch(beer : BeerEntity)

    @Delete
    suspend fun deleteBeer(beer: BeerEntity)
}

