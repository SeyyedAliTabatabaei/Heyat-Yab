package ir.heyatyab.presentation.navigation


import android.telecom.Call.Details
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ir.heyatyab.presentation.feature.details.DetailsRoute
import ir.heyatyab.presentation.feature.home.HomeRoute

sealed class HomeGraph(override val route: String) : NavGraphItem {
    data object HomeScreen : HomeGraph(
        route = "home_screen"
    )

    data object DetailsScreen : HomeGraph(
        route = "details_screen?eventID={eventID}"
    ) {
        fun routeWithArgs(id: Long?): String {
            return if (id != null) "details_screen?eventID=$id"
            else "details_screen"
        }
    }
}

fun NavGraphBuilder.homeNavGraph(navController: NavController){
    customComposable(route = HomeGraph.HomeScreen.route) {
        HomeRoute(
            navigateToEventDetails = {
                navController.navigate(HomeGraph.DetailsScreen.routeWithArgs(it))
            }
        )
    }

    customComposable(route = HomeGraph.DetailsScreen.route) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("eventID")?.toLongOrNull() ?: 0L
        DetailsRoute(
            eventId = id ,
            onBack = {
                navController.popBackStack()
            }
        )
    }

}
