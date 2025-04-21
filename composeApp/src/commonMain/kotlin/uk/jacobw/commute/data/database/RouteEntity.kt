package uk.jacobw.commute.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = StationEntity::class,
            parentColumns = ["crsCode"],
            childColumns = ["originCrsCode"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = StationEntity::class,
            parentColumns = ["crsCode"],
            childColumns = ["destinationCrsCode"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RouteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val originCrsCode: String,
    val destinationCrsCode: String,
)
