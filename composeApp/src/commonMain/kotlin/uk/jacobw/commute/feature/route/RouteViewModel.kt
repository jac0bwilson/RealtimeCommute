package uk.jacobw.commute.feature.route

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.asStateFlow
import uk.jacobw.commute.data.RouteRepository

class RouteViewModel(
    private val routeRepository: RouteRepository,
) : ViewModel() {
    private val _route = routeRepository.selectedRoute
    val route = _route.asStateFlow()
}