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
    val route by viewModel.route.collectAsStateWithLifecycle()
//    val locationsBeforeRoute by viewModel.locationsBeforeRoute.collectAsStateWithLifecycle()
//    val locationsOnRoute by viewModel.locationsOnRoute.collectAsStateWithLifecycle()
//    val locationsAfterRoute by viewModel.locationsAfterRoute.collectAsStateWithLifecycle()
    val locations by viewModel.locations.collectAsStateWithLifecycle()
    val (locationsBeforeRoute, locationsOnRoute, locationsAfterRoute) = locations
    val isLoadingLocations by viewModel.isLoading.collectAsStateWithLifecycle()

    LifecycleStartEffect(Unit) {
        viewModel.loadService()

        onStopOrDispose { }
    }

    ServiceLayout(
        route = route!!,
        locationsBeforeRoute = locationsBeforeRoute,
        locationsOnRoute = locationsOnRoute,
        locationsAfterRoute = locationsAfterRoute,
        isLoadingLocations = isLoadingLocations,
        onClickNavigationIcon = onClickNavigationIcon,
    )
}
