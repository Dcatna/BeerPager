package my.packlol.pagingjawn.presentation

import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.packlol.pagingjawn.local.BeerDao
import my.packlol.pagingjawn.local.BeerEntity
import my.packlol.pagingjawn.local.FavoritesDao
import my.packlol.pagingjawn.local.SearchDao
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val beerDao : BeerDao,
    private val favsDao : FavoritesDao,
    private val serachDao : SearchDao


) : ViewModel() {

    fun createSearch(text : String) : List<BeerEntity>{
        var emplis : List<BeerEntity>
        var returnLis : List<BeerEntity> = mutableListOf()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val lis : List<BeerEntity> = serachDao.observeAll()
                emplis = lis
            }
            for(beer in emplis){
                if(beer.toString().indexOf(text) != null){

                    returnLis += beer
                }
            }

            for(x in returnLis){
                serachDao.addSearch(x)
            }
        }

        return returnLis
    }


}