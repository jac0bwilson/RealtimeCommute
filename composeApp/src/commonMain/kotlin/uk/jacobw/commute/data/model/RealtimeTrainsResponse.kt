package uk.jacobw.commute.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val services: List<Service>
)

@Serializable
data class Service(
    @SerialName("locationDetail") val detail: Detail,
    val serviceUid: String,
    val runDate: String,
    @SerialName("atocName") val operator: String,
)

@Serializable
data class Detail(
    @SerialName("destination") val destinations: List<Destination>,
    @SerialName("gbttBookedDeparture") val plannedDeparture: String,
    val realtimeDeparture: String,
    val platform: String,
    val platformConfirmed: Boolean,
    val platformChanged: Boolean,
)

@Serializable
data class Destination(
    val description: String,
)
