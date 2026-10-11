package ua.kpi.industrial_room.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO-інтерфейс для виконання операцій доступу до даних у базі.
 */
@Dao
interface IndustrialDao {

    /**
     * Вставка нової установки в БД.
     */
    @Insert
    suspend fun insertStation(station: IndustrialStationEntity): Long

    /**
     * Вставка нового вимірювання в БД.
     */
    @Insert
    suspend fun insertMeasurement(measurement: ElectricalMeasurementEntity)

    /**
     * Видалення вимірювання з БД.
     */
    @Delete
    suspend fun deleteMeasurement(measurement: ElectricalMeasurementEntity)

    /**
     * Отримання потоку всіх установок, відсортованих за назвою.
     */
    @Query("SELECT * FROM stations ORDER BY name ASC")
    fun getAllStations(): Flow<List<IndustrialStationEntity>>

    /**
     * Отримання відфільтрованого потоку вимірювань для заданої установки та мінімальної потужності.
     */
    @Query("SELECT * FROM measurements WHERE stationId = :stationId AND powerKw >= :minPower")
    fun getFilteredMeasurements(stationId: Int, minPower: Double): Flow<List<ElectricalMeasurementEntity>>
}
