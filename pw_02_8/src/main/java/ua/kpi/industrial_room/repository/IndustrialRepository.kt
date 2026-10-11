package ua.kpi.industrial_room.repository

import kotlinx.coroutines.flow.Flow
import ua.kpi.industrial_room.data.ElectricalMeasurementEntity
import ua.kpi.industrial_room.data.IndustrialDao
import ua.kpi.industrial_room.data.IndustrialStationEntity

/**
 * Репозиторій, що надає єдину точку доступу до даних та ізолює DAO від ViewModel.
 */
class IndustrialRepository(private val dao: IndustrialDao) {

    /**
     * Отримати потік усіх установок.
     */
    fun getAllStations(): Flow<List<IndustrialStationEntity>> = dao.getAllStations()

    /**
     * Додати нову установку.
     */
    suspend fun insertStation(station: IndustrialStationEntity): Long = dao.insertStation(station)

    /**
     * Додати нове вимірювання.
     */
    suspend fun insertMeasurement(measurement: ElectricalMeasurementEntity) = dao.insertMeasurement(measurement)

    /**
     * Видалити вимірювання.
     */
    suspend fun deleteMeasurement(measurement: ElectricalMeasurementEntity) = dao.deleteMeasurement(measurement)

    /**
     * Отримати вимірювання з фільтрацією за мінімальною потужністю.
     */
    fun getFilteredMeasurements(stationId: Int, minPower: Double): Flow<List<ElectricalMeasurementEntity>> =
        dao.getFilteredMeasurements(stationId, minPower)
}
