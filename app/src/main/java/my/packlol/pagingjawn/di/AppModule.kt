package my.packlol.pagingjawn.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import my.packlol.pagingjawn.data.remote.BeerApi
import my.packlol.pagingjawn.data.remote.BeerRemoteMediator
import my.packlol.pagingjawn.local.BeerDao
import my.packlol.pagingjawn.local.BeerDatabse
import my.packlol.pagingjawn.local.BeerEntity
import my.packlol.pagingjawn.local.FavoritesDao
import my.packlol.pagingjawn.local.Favs
import my.packlol.pagingjawn.presentation.BeerVM
import my.packlol.pagingjawn.presentation.UserVM
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context : Context) : BeerDatabse{
        return Room.databaseBuilder(
            context,
            BeerDatabse::class.java,
            "beers.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBeerApi() : BeerApi{
        return Retrofit.Builder()
            .baseUrl(BeerApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideFavDao(beerdb : BeerDatabse) : FavoritesDao {
        return beerdb.favdao
    }

    @Singleton
    @Provides
    fun provideBeerDao(beerdb : BeerDatabse) : BeerDao {
        return beerdb.dao
    }

    @Singleton
    @Provides
    fun provideBeerVM(pager : Pager<Int, BeerEntity>,
                      dao: FavoritesDao,
                      beerdao : BeerDao) : BeerVM{

        return BeerVM(pager, dao, beerdao)
    }

    @Singleton
    @Provides
    fun provideUserVM( dao : FavoritesDao,
                      unsavedDao : BeerDao) : UserVM {

        return UserVM(dao, unsavedDao)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideBeerPager(beerDb : BeerDatabse, beerApi : BeerApi): Pager<Int, BeerEntity>{
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = BeerRemoteMediator(
                beerDb,
                beerApi
            ),
            pagingSourceFactory = {
                beerDb.dao.pagingSource()
            }
        )
    }
}