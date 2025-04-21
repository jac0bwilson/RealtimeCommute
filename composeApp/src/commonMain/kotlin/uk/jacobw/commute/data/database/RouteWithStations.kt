package uk.jacobw.commute.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class RouteWithStations(
    @Embedded val route: RouteEntity,

    @Relation(
        parentColumn = "originCrsCode",
        entityColumn = "crsCode"
    )
    val originStation: StationEntity,

    @Relation(
        parentColumn = "destinationCrsCode",
        entityColumn = "crsCode"
    )
    val destinationStation: StationEntity
)
