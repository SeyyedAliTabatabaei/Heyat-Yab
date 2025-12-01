package ir.heyatyab.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.heyatyab.presentation.feature.BottomNavigationBar
import ir.heyatyab.presentation.feature.BottomNavigationItem
import ir.heyatyab.presentation.navigation.HomeGraph
import ir.heyatyab.presentation.navigation.ProfileGraph
import ir.heyatyab.presentation.navigation.RootGraph
import ir.heyatyab.presentation.theme.HeyatYabTheme
import ir.heyatyab.presentation.theme.LocalCustomColors
import ir.heyatyab.presentation.theme.LocalSnackbarController
import ir.heyatyab.presentation.utils.SnackBarContent
import ir.heyatyab.presentation.utils.SnackBarController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HeyatYabTheme {
                ContentApp()
            }
        }
    }
}

@Composable
fun ContentApp(modifier: Modifier = Modifier) {
    val colors = LocalCustomColors.current

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val currentRouteBottomNavigation = remember(currentRoute) {
        when (currentRoute) {
            HomeGraph.HomeScreen.route -> BottomNavigationItem.Home.route
            ProfileGraph.ProfileScreen.route -> BottomNavigationItem.Profile.route
            else -> BottomNavigationItem.Home.route
        }
    }

    val visibleBottomNavigation by remember(currentRoute) {
        mutableStateOf(
            when (currentRoute) {
                HomeGraph.HomeScreen.route, ProfileGraph.ProfileScreen.route -> true
                else -> false
            }
        )
    }

    val snackbarController = remember { SnackBarController() }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        containerColor = colors.background,
        bottomBar = {
            BottomBar(visibleBottomNavigation, currentRouteBottomNavigation, navController)
        },
        snackbarHost = {
            SnackBar(snackbarController)
        } ,
        content = { innerPadding ->
            CompositionLocalProvider(LocalSnackbarController provides snackbarController) {
                RootGraph(navHostController = navController , modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding))
            }
        }
    )

}

@Composable
private fun SnackBar(snackbarController: SnackBarController) {
    AnimatedVisibility(
        visible = snackbarController.showSnackbar.value,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut(),
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        snackbarController.snackbarData?.let {
            SnackBarContent(snackBarType = it.snackBarType, text = it.message)
        }
    }
}

@Composable
private fun BottomBar(
    visibleBottomNavigation: Boolean,
    currentRouteBottomNavigation: String,
    navController: NavHostController
) {
    AnimatedVisibility(
        visible = visibleBottomNavigation,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        BottomNavigationBar(
            items = listOf(
                BottomNavigationItem.Home,
                BottomNavigationItem.Profile
            ),
            currentRoute = currentRouteBottomNavigation,
            onItemSelected = { selectedItem ->
                if (currentRouteBottomNavigation != selectedItem.route) {
                    navController.navigate(selectedItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HeyatYabTheme {

    }
}