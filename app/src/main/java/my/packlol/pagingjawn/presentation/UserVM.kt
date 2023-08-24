package my.packlol.pagingjawn.presentation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.packlol.pagingjawn.domain.Beer
import my.packlol.pagingjawn.domain.SavableBeer
import my.packlol.pagingjawn.local.FavoritesDao
import my.packlol.pagingjawn.local.Favs

class UserVM(val dao : FavoritesDao) : ViewModel() {

    val favsList = dao.observeAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
    )

    fun insert(beer: Favs){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                dao.insertBeer(beer)
            }


        }
    }
}