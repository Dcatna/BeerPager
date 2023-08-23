package my.packlol.pagingjawn.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import my.packlol.pagingjawn.domain.Beer


@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeer(beer : Beer)

    @Delete
    suspend fun deleteBeer(beer: Beer)

    @Query("Select * FROM Favs")
    fun observeAll(): Flow<List<Favs>>

}