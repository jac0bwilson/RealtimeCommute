package uk.jacobw.commute.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onNavigateToRoute: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    LifecycleStartEffect(Unit) {
        viewModel.loadStations()

        onStopOrDispose {}
    }

    val routes by viewModel.routes.collectAsStateWithLifecycle()
    val stations by viewModel.stations.collectAsStateWithLifecycle()

    HomeLayout(
        stationOptions = stations,
        routes = routes,
        addRoute = viewModel::addRoute,
        deleteAllRoutes = viewModel::deleteAllRoutes,
        onNavigateToRoute = { route ->
            viewModel.setSelectedRoute(route)
            onNavigateToRoute()
        },
    )
}
