package uk.jacobw.commute.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RouteScreen(
    viewModel: RouteViewModel = koinViewModel(),
    onClickNavigationIcon: () -> Unit,
) {
    val route by viewModel.route.collectAsStateWithLifecycle()
    val services by viewModel.services.collectAsStateWithLifecycle()
    val isLoadingServices by viewModel.isLoading.collectAsStateWithLifecycle()

    LifecycleStartEffect(route) {
        viewModel.loadServices()

        onStopOrDispose { }
    }

    RouteLayout(
        route = route!!,
        services = services,
        isLoadingServices = isLoadingServices,
        onClickNavigationIcon = onClickNavigationIcon,
        onClickReverseRoute = viewModel::reverseRoute,
    )
}
