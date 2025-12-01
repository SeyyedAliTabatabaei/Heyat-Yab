package ir.heyatyab.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.customComposable(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = { scaleIn(tween(300), initialScale = 0.9f) + fadeIn(tween(300)) },
        exitTransition = { scaleOut(tween(200), targetScale = 1.1f) + fadeOut(tween(200)) },
        popEnterTransition = { scaleIn(tween(300), initialScale = 0.9f) + fadeIn(tween(300)) },
        popExitTransition = { scaleOut(tween(200), targetScale = 1.1f) + fadeOut(tween(200)) },
        content = content
    )
}