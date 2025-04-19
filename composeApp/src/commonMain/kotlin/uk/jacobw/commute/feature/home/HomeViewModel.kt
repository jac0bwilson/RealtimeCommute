package uk.jacobw.commute.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
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
}