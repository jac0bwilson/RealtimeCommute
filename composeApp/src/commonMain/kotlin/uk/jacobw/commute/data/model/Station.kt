package uk.jacobw.commute.data.model

import kotlinx.serialization.Serializable
import uk.jacobw.commute.data.database.StationEntity

@Serializable
data class Station(
    val stationName: String,
    val crsCode: String,
) {
    fun toStationEntity(): StationEntity {
        return StationEntity(
            crsCode = crsCode,
            name = stationName,
        )
    }
}
