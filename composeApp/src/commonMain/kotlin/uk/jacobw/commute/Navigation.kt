package uk.jacobw.commute

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.jacobw.commute.feature.HomeScreen

fun NavGraphBuilder.featureGraph(
    navController: NavController,
) {
    composable(route = Routes.HOME.name) {
        HomeScreen()
    }
}

enum class Routes {
    HOME
}