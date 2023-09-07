package my.packlol.pagingjawn

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import my.packlol.pagingjawn.local.BeerDatabse
import my.packlol.pagingjawn.local.FavoritesDao
import my.packlol.pagingjawn.navigation.Navigation
import my.packlol.pagingjawn.presentation.BeerScreen
import my.packlol.pagingjawn.presentation.BeerVM
import my.packlol.pagingjawn.presentation.SearchVM
import my.packlol.pagingjawn.presentation.UserVM
import my.packlol.pagingjawn.ui.theme.PagingJawnTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagingJawnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel = hiltViewModel<BeerVM>()
                    val viewModelf = hiltViewModel<UserVM>()
                    val viewModelSearch = hiltViewModel<SearchVM>()

                    val beers = viewModel.beerPagingFlow.collectAsLazyPagingItems()

                    Navigation(viewModel, viewModelf, viewModelSearch)
                }
            }
        }
    }
}

