package uk.jacobw.commute

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.jacobw.commute.feature.home.HomeScreen
import uk.jacobw.commute.feature.route.RouteScreen

fun NavGraphBuilder.featureGraph(navController: NavController) {
    composable(route = Routes.HOME.name) {
        HomeScreen(
            onNavigateToRoute = { navController.navigate(Routes.ROUTE.name) },
        )
    }

    composable(route = Routes.ROUTE.name) {
        RouteScreen(
            onClickNavigationIcon = { navController.popBackStack() },
        )
    }
}

enum class Routes {
    HOME,
    ROUTE,
}
