package uk.jacobw.commute.data.network.station

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import uk.jacobw.commute.data.model.Station

class StationApi(
    private val httpClient: HttpClient,
) {
    suspend fun getStationList(): Result<List<Station>> {
        return try {
            Result.success(httpClient
                .get("https://huxley2.azurewebsites.net/crs")
                .body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}