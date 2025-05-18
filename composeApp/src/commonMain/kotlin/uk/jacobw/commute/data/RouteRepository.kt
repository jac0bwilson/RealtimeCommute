package uk.jacobw.commute.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import uk.jacobw.commute.data.database.RouteDao
import uk.jacobw.commute.data.model.Route

class RouteRepository(
    private val routeDao: RouteDao,
) {
    val selectedRoute: MutableStateFlow<Route?> = MutableStateFlow(null)

    suspend fun insertRoute(route: Route) {
        routeDao.insertStation(route.origin.toStationEntity())
        routeDao.insertStation(route.destination.toStationEntity())
        routeDao.insertRoute(route.toRouteEntity())
    }

    fun getAllRoutes() = routeDao.getAllRoutes().map { routes -> routes.map { it.toRoute() } }

    suspend fun deleteAllRoutes() = routeDao.deleteAll()
}
