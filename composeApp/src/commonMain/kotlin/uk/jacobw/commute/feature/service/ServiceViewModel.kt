package uk.jacobw.commute.feature.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.jacobw.commute.data.RealtimeTrainsRepository
import uk.jacobw.commute.data.RouteRepository
import uk.jacobw.commute.data.model.Location
import uk.jacobw.commute.data.model.Route

class ServiceViewModel(
    routeRepository: RouteRepository,
    private val realtimeTrainsRepository: RealtimeTrainsRepository,
) : ViewModel() {
    val route = routeRepository.selectedRoute.asStateFlow()
    private val service = realtimeTrainsRepository.selectedService

    private val _locations = MutableStateFlow<List<Location>>(emptyList())
    val locations =
        _locations
            .combine(route) { locations, route ->
                partitionLocations(locations, route!!)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Triple(emptyList(), emptyList(), emptyList()),
            )

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun loadService() {
        _isLoading.value = true
        service?.let { service ->
            viewModelScope.launch {
                realtimeTrainsRepository
                    .getServiceDetail(service.serviceUid, service.date)
                    .onSuccess { _locations.value = it }
            }
            _isLoading.value = false
        }
    }

    private fun partitionLocations(
        locations: List<Location>,
        route: Route,
    ): Triple<List<Location>, List<Location>, List<Location>> {
        val beforeOrigin = mutableListOf<Location>()
        val onRoute = mutableListOf<Location>()
        val afterDestination = mutableListOf<Location>()
        var passedOrigin = false
        var passedDestination = false

        locations.forEach {
            if (it.crs == route.origin.crsCode) {
                passedOrigin = true
            }

            when {
                passedOrigin.not() && passedDestination.not() -> beforeOrigin.add(it)
                passedOrigin && passedDestination.not() -> onRoute.add(it)
                passedOrigin && passedDestination -> afterDestination.add(it)
            }

            if (it.crs == route.destination.crsCode) {
                passedDestination = true
            }
        }

        return Triple(beforeOrigin.toList(), onRoute.toList(), afterDestination.toList())
    }
}
