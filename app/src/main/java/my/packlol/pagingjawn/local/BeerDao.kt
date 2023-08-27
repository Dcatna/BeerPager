package my.packlol.pagingjawn.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import my.packlol.pagingjawn.domain.SavableBeer

@Dao
interface BeerDao {
    @Upsert
    suspend fun upsertAll(beers : List<BeerEntity>)

    @Upsert
    suspend fun upsertSearched(beers : List<SavableBeer>)

    @Query("SELECT * FROM beerentity")
    fun pagingSource() : PagingSource<Int, BeerEntity>

    @Query("SElECT * FROM beerentity WHERE id=:id")
    suspend fun getById(id: Int) : BeerEntity

    @Query("SELECT * FROM beerentity WHERE name=:name")
    suspend fun getByName(name : String) : BeerEntity

    @Query("DELETE FROM beerentity")
    suspend fun clearAll()
}