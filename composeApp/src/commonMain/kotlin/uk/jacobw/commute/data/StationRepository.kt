package uk.jacobw.commute.data

import uk.jacobw.commute.data.model.Station
import uk.jacobw.commute.data.network.station.StationApi

class StationRepository(
    private val stationApi: StationApi,
) {
    private var stations: List<Station> = emptyList()

    suspend fun getStations(): Result<List<Station>> =
        if (stations.isEmpty()) {
            stationApi
                .getStationList()
                .onSuccess { stations = it }
        } else {
            Result.success(stations)
        }
}
