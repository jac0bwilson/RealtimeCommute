package uk.jacobw.commute.data

import kotlinx.datetime.LocalDate
import uk.jacobw.commute.data.model.Location
import uk.jacobw.commute.data.model.Service
import uk.jacobw.commute.data.network.realtimetrains.RealtimeTrainsApi

class RealtimeTrainsRepository(
    private val realtimeTrainsApi: RealtimeTrainsApi,
) {
    var selectedService: Service? = null

    suspend fun getNextServices(
        origin: String,
        destination: String,
    ): Result<List<Service>> = realtimeTrainsApi.getNextServices(origin, destination)

    suspend fun getServiceDetail(
        serviceId: String,
        date: LocalDate,
    ): Result<List<Location>> = realtimeTrainsApi.getServiceDetail(serviceId, date)
}
