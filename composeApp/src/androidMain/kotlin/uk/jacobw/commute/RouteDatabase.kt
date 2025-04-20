package uk.jacobw.commute

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.jacobw.commute.data.database.RouteDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<RouteDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("routes.db")

    return Room.databaseBuilder(
        context = appContext,
        name = dbFile.absolutePath
    )
}