package uk.jacobw.commute.feature.home

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToSample: () -> Unit,
) {
    HomeLayout(
        onNavigateToSample = onNavigateToSample,
        from = viewModel.fromStation,
        to = viewModel.toStation,
        updateFrom = viewModel::updateFromStation,
        updateTo = viewModel::updateToStation
    )
}