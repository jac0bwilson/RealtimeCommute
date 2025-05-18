package uk.jacobw.commute.data.database

import androidx.room.Embedded
import androidx.room.Relation
import uk.jacobw.commute.data.model.Route
import uk.jacobw.commute.data.model.Station

data class RouteWithStations(
    @Embedded val route: RouteEntity,
    @Relation(
        parentColumn = "originCrsCode",
        entityColumn = "crsCode",
    )
    val originStation: StationEntity,
    @Relation(
        parentColumn = "destinationCrsCode",
        entityColumn = "crsCode",
    )
    val destinationStation: StationEntity,
) {
    fun toRoute(): Route =
        Route(
            origin =
                Station(
                    crsCode = originStation.crsCode,
                    name = originStation.name,
                ),
            destination =
                Station(
                    crsCode = destinationStation.crsCode,
                    name = destinationStation.name,
                ),
        )
}
