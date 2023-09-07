package my.packlol.pagingjawn.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import my.packlol.pagingjawn.domain.Beer
import my.packlol.pagingjawn.domain.SavableBeer

@Dao
interface BeerDao {
    @Upsert
    suspend fun upsertAll(beers : List<BeerEntity>)

    @Query("SELECT * FROM beerentity")
    fun pagingSource() : PagingSource<Int, BeerEntity>

    @Query("SELECT * FROM beerentity")
    suspend fun getAll() : List<SavableBeer>

    @Query("SElECT * FROM beerentity WHERE id=:id")
    suspend fun getById(id: Int) : BeerEntity

    @Query("SELECT * FROM beerentity WHERE name=:name")
    suspend fun getByName(name : String) : BeerEntity

    @Query("DELETE FROM beerentity")
    suspend fun clearAll()
}