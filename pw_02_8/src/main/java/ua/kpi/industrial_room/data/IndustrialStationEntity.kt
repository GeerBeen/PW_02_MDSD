package ua.kpi.industrial_room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сутність для таблиці установок (stations).
 */
@Entity(tableName = "stations")
data class IndustrialStationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
