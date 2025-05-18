package uk.jacobw.commute.feature.route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.jacobw.commute.data.RealtimeTrainsRepository
import uk.jacobw.commute.data.RouteRepository
import uk.jacobw.commute.data.database.RouteEntity
import uk.jacobw.commute.data.database.RouteWithStations
import uk.jacobw.commute.data.model.Service

class RouteViewModel(
    routeRepository: RouteRepository,
    private val realtimeTrainsRepository: RealtimeTrainsRepository,
) : ViewModel() {
    private val _route = MutableStateFlow(routeRepository.selectedRoute)
    val route = _route.asStateFlow()

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services = _services.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun reverseRoute() {
        _route.update { current ->
            current?.let {
                RouteWithStations(
                    route =
                        RouteEntity(
                            originCrsCode = current.route.destinationCrsCode,
                            destinationCrsCode = current.route.originCrsCode,
                        ),
                    originStation = current.destinationStation,
                    destinationStation = current.originStation,
                )
            }
        }
    }

    fun loadServices() {
        _isLoading.value = true
        _route.value?.let { route ->
            viewModelScope.launch {
                realtimeTrainsRepository
                    .getNextServices(route.originStation.crsCode, route.destinationStation.crsCode)
                    .onSuccess { _services.value = it }

                _isLoading.value = false
            }
        }
    }

    fun selectService(service: Service) {
        realtimeTrainsRepository.selectedService = service
    }
}
