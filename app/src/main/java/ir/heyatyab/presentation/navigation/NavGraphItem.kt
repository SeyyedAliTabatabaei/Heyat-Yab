package ir.heyatyab.presentation.navigation


interface NavGraphItem {
    val route: String
}

interface NavGraph {
    fun getScreens(): List<NavGraphItem>
}