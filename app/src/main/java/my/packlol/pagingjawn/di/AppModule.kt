package my.packlol.pagingjawn.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.LazyPagingItems
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import my.packlol.pagingjawn.data.remote.BeerApi
import my.packlol.pagingjawn.data.remote.BeerRemoteMediator
import my.packlol.pagingjawn.data.remote.MyInterceptor
import my.packlol.pagingjawn.domain.SavableBeer
import my.packlol.pagingjawn.local.BeerDao
import my.packlol.pagingjawn.local.BeerDatabse
import my.packlol.pagingjawn.local.BeerEntity
import my.packlol.pagingjawn.local.FavoritesDao
import my.packlol.pagingjawn.local.Favs
import my.packlol.pagingjawn.local.SearchDao
import my.packlol.pagingjawn.presentation.BeerVM
import my.packlol.pagingjawn.presentation.SearchVM
import my.packlol.pagingjawn.presentation.UserVM
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.sql.Savepoint
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
    fun provideBeerApi(okHttpClient: OkHttpClient) : BeerApi{

        return Retrofit.Builder()
            .baseUrl("http://api.punkapi.com/v2/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun okhttpProvider() : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(MyInterceptor()).build()
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
    fun provideSearchDao(beerdb : BeerDatabse) : SearchDao{
        return beerdb.searchdao
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
    fun provideSearchVM(
        beerdao: BeerDao,
        favsdao : FavoritesDao,
        searchdao : SearchDao
    ) : SearchVM{
        return SearchVM(beerdao, favsdao, searchdao)
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