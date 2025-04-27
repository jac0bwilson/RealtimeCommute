package uk.jacobw.commute.data.network.realtimetrains

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import uk.jacobw.commute.data.model.Response
import uk.jacobw.commute.data.model.Service

class RealtimeTrainsApi(
    private val httpClient: HttpClient,
) {
    suspend fun getNextServices(
        origin: String,
        destination: String,
    ): Result<List<Service>> =
        try {
            Result.success(
                httpClient
                    .get("$BASE_URL/json/search/$origin/to/$destination")
                    .body<Response>()
                    .services,
            )
        } catch (e: Exception) {
            Result.failure(e)
        }

    private companion object {
        const val BASE_URL = "https://api.rtt.io/api/v1"
    }
}
