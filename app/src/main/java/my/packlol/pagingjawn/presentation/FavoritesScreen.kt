package my.packlol.pagingjawn.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.paging.compose.items
import my.packlol.pagingjawn.local.Favs
import my.packlol.pagingjawn.navigation.Screen
@Composable
fun FavoritesScreen(viewModel : UserVM , nav : (Screen)-> Unit) {
    val list = viewModel.favsList.collectAsState()
    Box() {
        Column() {
            favsLazyList(lis = list.value)
        }
    }
}

@Composable
fun favsLazyList(lis : List<Favs>) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(lis){beer ->
            Text(text = beer.toString(), modifier = Modifier.background(Color.Gray))
        }
    }

}