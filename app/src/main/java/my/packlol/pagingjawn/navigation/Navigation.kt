package my.packlol.pagingjawn.navigation

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
import my.packlol.pagingjawn.presentation.BeerScreen
import my.packlol.pagingjawn.presentation.BeerVM
import my.packlol.pagingjawn.presentation.LoginScreen

@Composable
fun Navigation(viewModel : BeerVM){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LogScreen.route){
        fadeComposable(route = Screen.LogScreen.route){
            LoginScreen(viewModel){ screen ->
                navController.navigate(screen.route)

            }
        }

        fadeComposable(route = Screen.beerScreen.route){
            BeerScreen(viewModel.beerPagingFlow.collectAsLazyPagingItems(), viewModel) { screen ->
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