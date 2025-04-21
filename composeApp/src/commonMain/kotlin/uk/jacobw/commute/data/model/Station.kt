package uk.jacobw.commute.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Station(
    val stationName: String,
    val crsCode: String,
)
