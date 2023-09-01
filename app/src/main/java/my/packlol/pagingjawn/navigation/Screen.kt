package my.packlol.pagingjawn.navigation

sealed class Screen(val route : String){
    object LogScreen : Screen("main_screen")
    object beerScreen : Screen("beer_screen")
    object FavsScreen : Screen("favs_screen")
    object SearchScreen : Screen("search_screen")
}
