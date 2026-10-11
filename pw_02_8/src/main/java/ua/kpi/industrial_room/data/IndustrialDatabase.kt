package ua.kpi.industrial_room.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Абстрактний клас бази даних Room, що об'єднує сутності установок та вимірювань.
 */
@Database(
    entities = [IndustrialStationEntity::class, ElectricalMeasurementEntity::class],
    version = 1,
    exportSchema = false
)
abstract class IndustrialDatabase : RoomDatabase() {

    /**
     * Повертає DAO для роботи з даними.
     */
    abstract fun industrialDao(): IndustrialDao
}
