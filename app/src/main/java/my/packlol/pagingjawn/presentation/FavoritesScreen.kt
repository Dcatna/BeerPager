package my.packlol.pagingjawn.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import my.packlol.pagingjawn.domain.SavableBeer
import my.packlol.pagingjawn.local.Favs
import my.packlol.pagingjawn.mappers.toSavableBeer
import my.packlol.pagingjawn.navigation.Screen
@Composable
fun FavoritesScreen(viewModel : UserVM , nav : (Screen)-> Unit) {
    val list = viewModel.favsList.collectAsState()
    Scaffold(
        topBar = {
            Button(onClick = {nav(Screen.beerScreen)}) {

            }
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                favsLazyList(lis = list.value, viewModel, nav)
            }

        },

    )
}

@Composable
fun favsLazyList(lis : List<Favs>, viewModel : UserVM, nav : (Screen)->Unit) {
    LazyColumn(
        //modifier = Modifier.noRippleClickable { nav(Screen.beerScreen) } ,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(lis){beer ->
            FavItem(
                viewModel::save,
                beer
            )
        }
    }

}

