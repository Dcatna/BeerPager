package my.packlol.pagingjawn.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.packlol.pagingjawn.local.BeerDao
import my.packlol.pagingjawn.local.BeerEntity
import my.packlol.pagingjawn.local.FavoritesDao
import my.packlol.pagingjawn.mappers.toFav
import my.packlol.pagingjawn.mappers.toSavableBeer
import javax.inject.Inject

@HiltViewModel
class BeerVM @Inject constructor(
    pager : Pager<Int, BeerEntity>,
    private val dao: FavoritesDao,
    private val beerdao : BeerDao
): ViewModel() {
    val beerPagingFlow = pager.flow.cachedIn(viewModelScope).combine(dao.observeAll()){ pagingData, saved ->
        pagingData.map { entity ->
            entity.toSavableBeer(saved.find {
                entity.id == it.id
        }!=null) }
    }

    fun save(id : Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                dao.insertBeer(beerdao.getById(id).toFav(true))

            }
        }
    }
}