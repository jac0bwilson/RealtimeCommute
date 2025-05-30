package uk.jacobw.commute.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.jacobw.commute.data.RouteRepository
import uk.jacobw.commute.data.StationRepository
import uk.jacobw.commute.data.model.Route
import uk.jacobw.commute.data.model.Station

class HomeViewModel(
    private val routeRepository: RouteRepository,
    private val stationRepository: StationRepository,
) : ViewModel() {
    val routes: StateFlow<List<Route>> =
        routeRepository
            .getAllRoutes()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )
    private val _stations = MutableStateFlow<List<Station>>(emptyList())
    val stations = _stations.asStateFlow()

    fun loadStations() {
        viewModelScope.launch {
            stationRepository
                .getStations()
                .onSuccess { _stations.value = it }
        }
    }

    private fun findStationByString(value: String): Station? =
        stations.value.find {
            it.crsCode.lowercase() == value.lowercase() || it.name.lowercase() == value.lowercase()
        }

    fun addRoute(
        origin: String,
        destination: String,
    ): Boolean {
        val originStation = findStationByString(origin)
        val destinationStation = findStationByString(destination)

        if (originStation != null && destinationStation != null) {
            viewModelScope.launch {
                routeRepository.insertRoute(Route(originStation, destinationStation))
            }
            return true
        }

        return false
    }

    fun deleteAllRoutes() {
        viewModelScope.launch {
            routeRepository.deleteAllRoutes()
        }
    }

    fun setSelectedRoute(route: Route) {
        routeRepository.selectedRoute.value = route
    }
}
