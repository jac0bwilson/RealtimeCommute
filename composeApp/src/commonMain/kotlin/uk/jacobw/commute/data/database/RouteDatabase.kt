package uk.jacobw.commute.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [RouteEntity::class, StationEntity::class], version = 2)
@ConstructedBy(RouteDatabaseConstructor::class)
abstract class RouteDatabase : RoomDatabase() {
    abstract fun getDao(): RouteDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object RouteDatabaseConstructor : RoomDatabaseConstructor<RouteDatabase> {
    override fun initialize(): RouteDatabase
}

fun getRouteDatabase(builder: RoomDatabase.Builder<RouteDatabase>): RouteDatabase =
    builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(true)
        .build()
