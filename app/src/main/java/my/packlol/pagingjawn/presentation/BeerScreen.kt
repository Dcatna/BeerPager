package my.packlol.pagingjawn.presentation

import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import my.packlol.pagingjawn.domain.Beer
import my.packlol.pagingjawn.domain.SavableBeer
import my.packlol.pagingjawn.local.BeerEntity
import my.packlol.pagingjawn.mappers.toSavableBeer
import my.packlol.pagingjawn.navigation.Navigation
import my.packlol.pagingjawn.navigation.Screen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BeerScreen(
    beers : LazyPagingItems<SavableBeer>,
    viewModelB: BeerVM,
    searchVM : SearchVM,
    nav : (Screen) -> Unit

) {

    //errors ina toast
    val context = LocalContext.current

    LaunchedEffect(key1 = beers.loadState){
        if (beers.loadState.refresh is LoadState.Error){
            println((beers.loadState.refresh as LoadState.Error).error.message)
            Toast.makeText(context,
                "Error" + (beers.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        if(beers.loadState.refresh is LoadState.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

        }else{
            var text by remember{ mutableStateOf("") }
            val viewModel = searchVM
            val searchText by viewModel.searchText.collectAsState()
            val searchBeers by viewModel.beers.collectAsState()
            val isSearching by viewModel.isSearching.collectAsState()


            Scaffold(
                topBar = {
                    Row() {
                        TextField(
                            value = searchText,
                            onValueChange =
                            viewModel::onSearchTextChanged,




                            modifier = Modifier
                                .weight(3f)
                                .fillMaxWidth(),
                            placeholder = {Text(text = "Search")}

                        )
                        Button(onClick = {
                            //searchVM.makeSearch(text)
                            nav(Screen.SearchScreen) }) {

                        }
                        Button(
                            onClick = {nav(Screen.FavsScreen)},
                            modifier = Modifier.weight(1.5f)
                        ) {
                            Text("FAVORITES")
                        }
                    }




                },
                content = { padding ->
                    if (searchText.isBlank()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,


                            ) {
                            items(beers) { beer ->
                                if (beer != null) {
                                    BeerItem(
                                        beer = beer,
                                        modifier = Modifier.fillMaxWidth(),
                                        onSaveClick = viewModelB::save

                                    )
                                }

                            }
                            item {
                                if (beers.loadState.append is LoadState.Loading) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }else{

                        LazyColumn(modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                            ){
                            items(searchBeers){beer ->
                                BeerItem(onSaveClick = viewModelB::save, beer = beer)
                            }
                        }

                    }

            }

            )
        }
    }
    fun Modifier.noRippleClickable(action : ()-> Unit) : Modifier{
        return this.composed {
            clickable(
                interactionSource = remember{
                    MutableInteractionSource()
                },
                indication = null
            ) {
                action()
            }
        }

    }
}


