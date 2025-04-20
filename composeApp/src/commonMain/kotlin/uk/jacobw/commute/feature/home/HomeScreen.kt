package uk.jacobw.commute.feature.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToSample: () -> Unit,
) {
    val routes = viewModel.state.collectAsStateWithLifecycle()

    HomeLayout(
        from = viewModel.fromStation,
        to = viewModel.toStation,
        routes = routes.value,
        onNavigateToSample = onNavigateToSample,
        updateFrom = viewModel::updateFromStation,
        updateTo = viewModel::updateToStation,
        addRoute = viewModel::addRoute,
        deleteAllRoutes = viewModel::deleteAllRoutes,
    )
}