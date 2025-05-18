package uk.jacobw.commute.feature.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.jacobw.commute.data.RealtimeTrainsRepository
import uk.jacobw.commute.data.RouteRepository
import uk.jacobw.commute.data.model.Location

class ServiceViewModel(
    routeRepository: RouteRepository,
    private val realtimeTrainsRepository: RealtimeTrainsRepository,
) : ViewModel() {
    val route = routeRepository.selectedRoute
    private val service = realtimeTrainsRepository.selectedService

    private val _locations = MutableStateFlow<List<Location>>(emptyList())
    val locations = _locations.asStateFlow()

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
}
