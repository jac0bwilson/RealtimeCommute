package uk.jacobw.commute.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {
    @Insert
    suspend fun insert(item: RouteEntity)

    @Query("SELECT * FROM RouteEntity")
    fun getAllRoutes(): Flow<List<RouteEntity>>

    @Query("DELETE FROM RouteEntity")
    suspend fun deleteAll()
}