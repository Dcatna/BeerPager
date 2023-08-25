package my.packlol.pagingjawn.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.packlol.pagingjawn.local.BeerDao
import my.packlol.pagingjawn.local.FavoritesDao
import my.packlol.pagingjawn.local.Favs
import my.packlol.pagingjawn.mappers.toFav
import javax.inject.Inject

@HiltViewModel
class UserVM @Inject constructor(
    private val dao : FavoritesDao,
    private val unsavedDao : BeerDao) : ViewModel() {

    val favsList = dao.observeAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
    )


    fun save(id : Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                dao.insertBeer(unsavedDao.getById(id).toFav(true))

            }
        }
    }
}