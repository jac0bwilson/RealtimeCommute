package uk.jacobw.commute

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uk.jacobw.commute.feature.home.HomeScreen
import uk.jacobw.commute.feature.route.RouteScreen
import uk.jacobw.commute.feature.service.ServiceScreen

fun NavGraphBuilder.featureGraph(navController: NavController) {
    composable<NavigationRoutes.HomeScreen> {
        HomeScreen(
            onNavigateToRoute = { navController.navigate(NavigationRoutes.RouteScreen) },
        )
    }

    composable<NavigationRoutes.RouteScreen> {
        RouteScreen(
            onClickNavigationIcon = { navController.popBackStack() },
            onNavigateToService = { navController.navigate(NavigationRoutes.ServiceScreen) },
        )
    }

    composable<NavigationRoutes.ServiceScreen> {
        ServiceScreen(
            onClickNavigationIcon = { navController.popBackStack() },
        )
    }
}

sealed class NavigationRoutes {
    @Serializable data object HomeScreen : NavigationRoutes()

    @Serializable data object RouteScreen : NavigationRoutes()

    @Serializable data object ServiceScreen : NavigationRoutes()
}
