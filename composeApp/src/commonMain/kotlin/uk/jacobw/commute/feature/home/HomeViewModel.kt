package uk.jacobw.commute.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.jacobw.commute.data.RouteRepository
import uk.jacobw.commute.data.database.RouteEntity

class HomeViewModel(
    private val routeRepository: RouteRepository
) : ViewModel() {
    val state: StateFlow<List<RouteEntity>> =
        routeRepository.getAllRoutes()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    var fromStation by mutableStateOf("")
        private set
    var toStation by mutableStateOf("")
        private set

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