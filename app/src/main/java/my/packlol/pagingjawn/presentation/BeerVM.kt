package my.packlol.pagingjawn.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import my.packlol.pagingjawn.local.BeerEntity
import my.packlol.pagingjawn.local.FavoritesDao
import my.packlol.pagingjawn.mappers.toSavableBeer
import javax.inject.Inject

@HiltViewModel
class BeerVM @Inject constructor(
    pager : Pager<Int, BeerEntity>,
    dao: FavoritesDao
): ViewModel() {
    val beerPagingFlow = pager.flow.combine(dao.observeAll()){ pagingData, saved ->
        pagingData.map { entity ->
            entity.toSavableBeer(saved.find {
                entity.id == it.id
        }?.saved ?: false) }

    }.cachedIn(viewModelScope)
}