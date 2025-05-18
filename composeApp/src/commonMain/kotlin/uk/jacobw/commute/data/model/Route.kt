package uk.jacobw.commute.data.model

import uk.jacobw.commute.data.database.RouteEntity

data class Route(
    val origin: Station,
    val destination: Station,
) {
    fun toRouteEntity(): RouteEntity =
        RouteEntity(
            originCrsCode = origin.crsCode,
            destinationCrsCode = destination.crsCode,
        )
}
