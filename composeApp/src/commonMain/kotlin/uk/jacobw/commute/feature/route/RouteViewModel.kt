package uk.jacobw.commute.feature.route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.jacobw.commute.data.RealtimeTrainsRepository
import uk.jacobw.commute.data.RouteRepository
import uk.jacobw.commute.data.model.Route
import uk.jacobw.commute.data.model.Service

class RouteViewModel(
    private val routeRepository: RouteRepository,
    private val realtimeTrainsRepository: RealtimeTrainsRepository,
) : ViewModel() {
    val route = routeRepository.selectedRoute.asStateFlow()

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services = _services.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun reverseRoute() {
        routeRepository.selectedRoute.update { current ->
            current?.let {
                Route(
                    origin = current.destination,
                    destination = current.origin,
                )
            }
        }
    }

    fun loadServices() {
        _isLoading.value = true
        routeRepository.selectedRoute.value?.let { route ->
            viewModelScope.launch {
                realtimeTrainsRepository
                    .getNextServices(route.origin.crsCode, route.destination.crsCode)
                    .onSuccess { _services.value = it }

                _isLoading.value = false
            }
        }
    }

    fun selectService(service: Service) {
        realtimeTrainsRepository.selectedService = service
    }
}
