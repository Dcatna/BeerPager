package my.packlol.pagingjawn.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
import my.packlol.pagingjawn.mappers.toSavableBeer
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

    fun search(name : String) : Favs?{
        var res = MutableLiveData<Favs>()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = dao.getByName(name)
                res.value = data
            }
        }
        return res.value
    }

    fun save(id : Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                dao.insertBeer(unsavedDao.getById(id).toFav(true))

            }
        }
    }

    fun delete(id: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                dao.deleteBeer(unsavedDao.getById(id).toFav(false))
            }
        }
    }
}