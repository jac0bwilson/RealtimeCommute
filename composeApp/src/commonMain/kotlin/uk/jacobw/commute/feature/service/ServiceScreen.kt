package uk.jacobw.commute.feature.service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ServiceScreen(
    onClickNavigationIcon: () -> Unit,
    viewModel: ServiceViewModel = koinViewModel(),
) {
    val route = viewModel.route
    val locations by viewModel.locations.collectAsStateWithLifecycle()
    val isLoadingLocations by viewModel.isLoading.collectAsStateWithLifecycle()

    LifecycleStartEffect(Unit) {
        viewModel.loadService()

        onStopOrDispose { }
    }

    ServiceLayout(
        route = route!!,
        locations = locations,
        isLoadingLocations = isLoadingLocations,
        onClickNavigationIcon = onClickNavigationIcon,
    )
}
