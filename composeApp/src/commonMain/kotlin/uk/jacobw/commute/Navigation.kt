package uk.jacobw.commute

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.jacobw.commute.feature.home.HomeLayout
import uk.jacobw.commute.feature.home.HomeScreen
import uk.jacobw.commute.feature.sample.SampleScreen

fun NavGraphBuilder.featureGraph(
    navController: NavController,
) {
    composable(route = Routes.HOME.name) {
        HomeScreen(
            onNavigateToSample = { navController.navigate(Routes.SAMPLE.name) }
        )
    }

    composable(route = Routes.SAMPLE.name) {
        SampleScreen(
            onNavigationIconPressed = { navController.popBackStack() }
        )
    }
}

enum class Routes {
    HOME,
    SAMPLE,
}