package uk.jacobw.commute.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {
    @Insert
    suspend fun insertRoute(item: RouteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStation(item: StationEntity)

    @Query("SELECT * FROM RouteEntity")
    fun getAllRoutes(): Flow<List<RouteWithStations>>

    @Query("DELETE FROM RouteEntity")
    suspend fun deleteAll()
}
