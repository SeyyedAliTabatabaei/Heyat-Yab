package ir.heyatyab.presentation.navigation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ir.heyatyab.presentation.theme.LocalCustomColors

sealed class ProfileGraph(override val route: String) : NavGraphItem {
    data object ProfileScreen : ProfileGraph(
        route = "profile_screen"
    )
}

fun NavGraphBuilder.profileNavGraph(navController: NavController){
    customComposable(route = ProfileGraph.ProfileScreen.route) {
        Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
            Text(
                text = "حساب کاربری" ,
                style = MaterialTheme.typography.titleSmall ,
                color = LocalCustomColors.current.textPrimary
            )
        }

    }

}
