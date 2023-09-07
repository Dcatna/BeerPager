package my.packlol.pagingjawn.presentation

import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.packlol.pagingjawn.domain.SavableBeer
import my.packlol.pagingjawn.local.BeerDao
import my.packlol.pagingjawn.local.BeerEntity
import my.packlol.pagingjawn.local.FavoritesDao
import my.packlol.pagingjawn.local.SearchDao
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val beerDao : BeerDao,
    private val favsDao : FavoritesDao,
    private val searchDao : SearchDao,



) : ViewModel() {
    var providedBeers : LazyPagingItems<SavableBeer>? = null
    var allBeers : List<SavableBeer>? = null
    fun getAllBeers(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                allBeers = beerDao.getAll()
            }
        }
    }

    fun getBeers(beers : LazyPagingItems<SavableBeer>){
        providedBeers = beers
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _beers = MutableStateFlow(allBeers)
    val beers = searchText.combine(_beers){ text, beers ->
        if(text.isBlank()){
            beers
        }else{
            beers?.filter {
                it.doesMatchSearchQuery(text)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _beers.value
    )
    fun onSearchTextChanged(text: String){
        _searchText.value = text
    }

}

