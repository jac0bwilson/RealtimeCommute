package uk.jacobw.commute.data.network.realtimetrains

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.LocalDate
import uk.jacobw.commute.data.model.Location
import uk.jacobw.commute.data.model.Service
import uk.jacobw.commute.data.model.ServiceDetailResponse
import uk.jacobw.commute.data.model.ServicesResponse

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
                    .body<ServicesResponse>()
                    .services,
            )
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun getServiceDetail(
        serviceId: String,
        date: LocalDate,
    ): Result<List<Location>> =
        try {
            Result.success(
                httpClient
                    .get("$BASE_URL/json/service/$serviceId/${dateFormat.format(date)}")
                    .body<ServiceDetailResponse>()
                    .locations,
            )
        } catch (e: Exception) {
            Result.failure(e)
        }

    private companion object {
        const val BASE_URL = "https://api.rtt.io/api/v1"
        val dateFormat =
            LocalDate.Format {
                year()
                chars("/")
                monthNumber()
                chars("/")
                dayOfMonth()
            }
    }
}
