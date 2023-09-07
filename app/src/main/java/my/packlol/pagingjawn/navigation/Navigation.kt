package my.packlol.pagingjawn.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import my.packlol.pagingjawn.domain.SavableBeer
import my.packlol.pagingjawn.presentation.BeerScreen
import my.packlol.pagingjawn.presentation.BeerVM
import my.packlol.pagingjawn.presentation.FavoritesScreen
import my.packlol.pagingjawn.presentation.LoginScreen
import my.packlol.pagingjawn.presentation.SearchItem
import my.packlol.pagingjawn.presentation.SearchScreen
import my.packlol.pagingjawn.presentation.SearchVM
import my.packlol.pagingjawn.presentation.UserVM

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Navigation(viewModelB : BeerVM, viewModelF : UserVM, viewModelSearchVM: SearchVM){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LogScreen.route){
        fadeComposable(route = Screen.LogScreen.route){
            LoginScreen(viewModelB){ screen ->
                navController.navigate(screen.route)

            }
        }

        fadeComposable(route = Screen.beerScreen.route){
            BeerScreen(viewModelB.beerPagingFlow.collectAsLazyPagingItems(), viewModelB, viewModelSearchVM) { screen ->
                navController.navigate(screen.route)
            }
        }

        fadeComposable(route = Screen.FavsScreen.route){
            FavoritesScreen(viewModelF){ screen ->
                navController.navigate(screen.route)
            }
        }

        fadeComposable(route = Screen.SearchScreen.route){
            SearchScreen(searchVM = viewModelSearchVM, userVM = viewModelF, ""){ screen ->
                navController.navigate(screen.route)

            }
        }



    }

}

fun NavGraphBuilder.fadeComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit){
    composable(route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
        content = content
    )
}