package uk.jacobw.commute

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.jacobw.commute.feature.Home
import uk.jacobw.commute.feature.Sample

fun NavGraphBuilder.featureGraph(
    navController: NavController,
) {
    composable(route = Routes.HOME.name) {
        Home(
            onNavigateToSample = { navController.navigate(Routes.SAMPLE.name) }
        )
    }

    composable(route = Routes.SAMPLE.name) {
        Sample(
            onNavigationIconPressed = { navController.popBackStack() }
        )
    }
}

enum class Routes {
    HOME,
    SAMPLE,
}