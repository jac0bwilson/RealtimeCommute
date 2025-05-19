package uk.jacobw.commute.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uk.jacobw.commute.data.database.StationEntity

@Serializable
data class Station(
    @SerialName("stationName") val name: String,
    val crsCode: String,
) {
    fun toStationEntity(): StationEntity =
        StationEntity(
            crsCode = crsCode,
            name = name,
        )
}
