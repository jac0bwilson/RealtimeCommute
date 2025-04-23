package uk.jacobw.commute.data

import uk.jacobw.commute.data.model.Service
import uk.jacobw.commute.data.network.realtimetrains.RealtimeTrainsApi

class RealtimeTrainsRepository(
    private val realtimeTrainsApi: RealtimeTrainsApi,
) {
    suspend fun getNextServices(origin: String, destination: String): Result<List<Service>> =
        realtimeTrainsApi.getNextServices(origin, destination)
}