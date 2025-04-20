package uk.jacobw.commute.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RouteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val from: String,
    val to: String,
)
