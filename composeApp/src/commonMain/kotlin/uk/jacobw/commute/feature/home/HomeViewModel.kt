package uk.jacobw.commute.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import uk.jacobw.commute.data.database.RouteEntity
import uk.jacobw.commute.data.model.Station

class HomeViewModel(
    private val routeRepository: RouteRepository,
    private val stationRepository: StationRepository,
) : ViewModel() {
    val routes: StateFlow<List<RouteEntity>> =
        routeRepository.getAllRoutes()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    private val _stations = MutableStateFlow<List<Station>>(emptyList())
    val stations = _stations.asStateFlow()

    var fromStation by mutableStateOf("")
        private set
    var toStation by mutableStateOf("")
        private set

    fun loadStations() {
        viewModelScope.launch {
            stationRepository.getStations()
                .onSuccess { _stations.value = it }
        }
    }

    fun updateFromStation(value: String) {
        fromStation = value
    }

    fun updateToStation(value: String) {
        toStation = value
    }

    fun addRoute() {
        viewModelScope.launch {
            routeRepository.insertRoute(fromStation.trim(), toStation.trim())
        }

        fromStation = ""
        toStation = ""
    }

    fun deleteAllRoutes() {
        viewModelScope.launch {
            routeRepository.deleteAllRoutes()
        }
    }
}