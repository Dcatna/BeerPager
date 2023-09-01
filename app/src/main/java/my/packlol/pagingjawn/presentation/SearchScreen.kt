package my.packlol.pagingjawn.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import my.packlol.pagingjawn.local.BeerEntity
import my.packlol.pagingjawn.local.Favs
import my.packlol.pagingjawn.mappers.toSavableBeer
import my.packlol.pagingjawn.navigation.Screen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SearchScreen(
    searchVM: SearchVM,
    userVM : UserVM,
    text : String,
    nav : (Screen) -> Unit

) {
    val searchList = searchVM.createSearch(text)

    Scaffold(
        topBar = {
            Button(onClick = {nav(Screen.beerScreen)}) {
                Text(text = "BACK")
            }
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                SearchLazyList(lis = searchList, userVM)
            }

        },

        )
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SearchLazyList(lis : List<BeerEntity>, viewModel : UserVM) {
    LazyColumn(
        //modifier = Modifier.noRippleClickable { nav(Screen.beerScreen) } ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(lis) { beer ->
            SearchItem(
                viewModel::save,
                beer.toSavableBeer(false)

            )
        }
    }
}

