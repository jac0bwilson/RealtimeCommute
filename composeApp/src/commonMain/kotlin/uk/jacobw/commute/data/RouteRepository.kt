package uk.jacobw.commute.data

import uk.jacobw.commute.data.database.RouteDao
import uk.jacobw.commute.data.database.RouteEntity
import uk.jacobw.commute.data.database.RouteWithStations
import uk.jacobw.commute.data.model.Station

class RouteRepository(
    private val routeDao: RouteDao
) {
    var selectedRoute: RouteWithStations? = null

    suspend fun insertRoute(origin: Station, destination: Station) {
        routeDao.insertStation(origin.toStationEntity())
        routeDao.insertStation(destination.toStationEntity())
        routeDao.insertRoute(
            RouteEntity(
                originCrsCode = origin.crsCode,
                destinationCrsCode = destination.crsCode,
            )
        )
    }

    fun getAllRoutes() = routeDao.getAllRoutes()

    suspend fun deleteAllRoutes() = routeDao.deleteAll()
}