package ua.kpi.industrial_room.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Сутність для таблиці електричних вимірювань (measurements).
 * Пов'язана з таблицею установок через зовнішній ключ з каскадним видаленням.
 */
@Entity(
    tableName = "measurements",
    foreignKeys = [
        ForeignKey(
            entity = IndustrialStationEntity::class,
            parentColumns = ["id"],
            childColumns = ["stationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["stationId"])]
)
data class ElectricalMeasurementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val stationId: Int,
    val powerKw: Double
)
