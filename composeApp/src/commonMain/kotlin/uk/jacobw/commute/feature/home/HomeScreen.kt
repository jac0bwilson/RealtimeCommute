package uk.jacobw.commute.feature.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToSample: () -> Unit,
) {
    LifecycleStartEffect(Unit) {
        viewModel.loadStations()

        onStopOrDispose {}
    }

    val routes = viewModel.routes.collectAsStateWithLifecycle()
    val stations = viewModel.stations.collectAsStateWithLifecycle()

    HomeLayout(
        stationOptions = stations.value,
        routes = routes.value,
        onNavigateToSample = onNavigateToSample,
        addRoute = viewModel::addRoute,
        deleteAllRoutes = viewModel::deleteAllRoutes,
    )
}