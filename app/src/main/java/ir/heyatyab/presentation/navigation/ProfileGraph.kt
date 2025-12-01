package ir.heyatyab.presentation.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

sealed class ProfileGraph(override val route: String) : NavGraphItem {
    data object ProfileScreen : ProfileGraph(
        route = "profile_screen"
    )
}

fun NavGraphBuilder.profileNavGraph(navController: NavController){
    customComposable(route = HomeGraph.HomeScreen.route) {

    }

}
