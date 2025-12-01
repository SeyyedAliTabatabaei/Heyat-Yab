package ir.heyatyab.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import ir.heyatyab.presentation.feature.BottomNavigationItem

@Composable
fun RootGraph(navHostController: NavHostController , modifier: Modifier = Modifier) {

    NavHost(
        navController = navHostController ,
        startDestination = BottomNavigationItem.Home.route ,
        modifier = modifier
    ) {
        navigation(startDestination = HomeGraph.HomeScreen.route , route = BottomNavigationItem.Home.route) {
            homeNavGraph(navHostController)
        }

        navigation(startDestination = ProfileGraph.ProfileScreen.route , route = BottomNavigationItem.Profile.route){
            profileNavGraph(navHostController)
        }
    }
}