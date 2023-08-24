package my.packlol.pagingjawn.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import my.packlol.pagingjawn.local.BeerDatabse
import my.packlol.pagingjawn.local.BeerEntity
import my.packlol.pagingjawn.local.Favs
import my.packlol.pagingjawn.mappers.toBeerEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDb : BeerDatabse,
    private val beerApi: BeerApi
) : RemoteMediator<Int, BeerEntity>(){

    //whenever loading from paging this is called
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try{
            val loadKey = when(loadType){
                LoadType.REFRESH -> 1

                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem==null){
                        1
                    }else{
                        (lastItem.id/state.config.pageSize + 1)
                    }
                }
            }

            val beers = beerApi.getBeers(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            beerDb.withTransaction {
                if(loadType== LoadType.REFRESH){
                    beerDb.dao.clearAll()
                }

                val beerEntities = beers.map{it.toBeerEntity()}


                beerDb.dao.upsertAll(beerEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = beers.isEmpty()
            )
        } catch(e: IOException){
            MediatorResult.Error(e)
        } catch (e: HttpException){
            MediatorResult.Error(e)
        }

    }
}