package uk.jacobw.commute.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StationEntity(
    @PrimaryKey val crsCode: String,
    val name: String,
)
