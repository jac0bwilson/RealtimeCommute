package uk.jacobw.commute.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServicesResponse(
    val services: List<Service>,
)

@Serializable
data class ServiceDetailResponse(
    val locations: List<Location>,
)

@Serializable
data class Service(
    @SerialName("locationDetail") val location: Location,
    val serviceUid: String,
    @SerialName("atocName") val operator: String,
    @SerialName("runDate") private val _runDate: String,
) {
    val date = LocalDate.parse(_runDate)
}

@Serializable
data class Location(
    @SerialName("destination") val destinations: List<Destination>,
    @SerialName("gbttBookedArrival") val plannedArrival: String? = null,
    @SerialName("gbttBookedDeparture") val plannedDeparture: String? = null,
    val crs: String,
    val description: String,
    val realtimeArrival: String? = null,
    val realtimeDeparture: String? = null,
    val platform: String? = null,
    val platformConfirmed: Boolean = false,
    val platformChanged: Boolean = false,
)

@Serializable
data class Destination(
    val description: String,
)
