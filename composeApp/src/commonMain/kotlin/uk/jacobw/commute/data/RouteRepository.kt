package uk.jacobw.commute.data

import uk.jacobw.commute.data.database.RouteDao
import uk.jacobw.commute.data.database.RouteEntity

class RouteRepository(
    private val routeDao: RouteDao
) {
    suspend fun insertRoute(from: String, to: String) {
        routeDao.insert(
            RouteEntity(
                from = from,
                to = to,
            )
        )
    }

    fun getAllRoutes() = routeDao.getAllRoutes()

    suspend fun deleteAllRoutes() = routeDao.deleteAll()
}