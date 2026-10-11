package ua.kpi.industrial_room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.kpi.industrial_room.data.ElectricalMeasurementEntity
import ua.kpi.industrial_room.data.IndustrialStationEntity
import ua.kpi.industrial_room.repository.IndustrialRepository

/**
 * ViewModel для управління станом UI та викликів операцій над даними.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class IndustrialViewModel(private val repository: IndustrialRepository) : ViewModel() {

    /**
     * Список усіх установок.
     */
    val stations: StateFlow<List<IndustrialStationEntity>> = repository.getAllStations()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * ID обраної установки.
     */
    val selectedStationId = MutableStateFlow<Int?>(null)

    /**
     * Поріг фільтрації за потужністю.
     */
    val minPowerFilter = MutableStateFlow(0.0)

    /**
     * Потік вимірювань для обраної установки із застосуванням фільтра.
     */
    val measurements: StateFlow<List<ElectricalMeasurementEntity>> = combine(
        selectedStationId,
        minPowerFilter
    ) { stationId, minPower ->
        stationId to minPower
    }.flatMapLatest { (stationId, minPower) ->
        if (stationId == null) {
            flowOf(emptyList())
        } else {
            repository.getFilteredMeasurements(stationId, minPower)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    /**
     * Метод додавання установки.
     */
    fun addStation(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.insertStation(IndustrialStationEntity(name = name.trim()))
        }
    }

    /**
     * Метод додавання заміру потужності.
     */
    fun addMeasurement(stationId: Int, powerKw: Double) {
        viewModelScope.launch {
            repository.insertMeasurement(
                ElectricalMeasurementEntity(stationId = stationId, powerKw = powerKw)
            )
        }
    }

    /**
     * Метод видалення вимірювання.
     */
    fun deleteMeasurement(measurement: ElectricalMeasurementEntity) {
        viewModelScope.launch {
            repository.deleteMeasurement(measurement)
        }
    }

    /**
     * Метод вибору активної установки.
     */
    fun selectStation(id: Int) {
        selectedStationId.value = id
    }

    /**
     * Метод встановлення значення фільтра за потужністю.
     */
    fun setFilter(minPower: Double) {
        minPowerFilter.value = minPower
    }
}

/**
 * Фабрика для створення екземпляра IndustrialViewModel з репозиторієм.
 */
class IndustrialViewModelFactory(
    private val repository: IndustrialRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IndustrialViewModel::class.java)) {
            return IndustrialViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
