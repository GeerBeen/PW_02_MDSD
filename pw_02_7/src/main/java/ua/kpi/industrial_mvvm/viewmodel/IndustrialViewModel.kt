package ua.kpi.industrial_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.kpi.industrial_mvvm.data.ElectricalParams
import ua.kpi.industrial_mvvm.data.IndustrialState
import java.util.Locale
import kotlin.random.Random

/**
 * ViewModel для управління станом та бізнес-логікою промислової електроустановки.
 *
 * Зберігає та оновлює стан у StateFlow, що забезпечує збереження стану при зміні
 * конфігурації пристрою (поворот екрана) та реактивну підписку в UI через collectAsState.
 */
class IndustrialViewModel : ViewModel() {

    // Приватний MutableStateFlow для зміни стану тільки зсередини ViewModel
    private val _uiState = MutableStateFlow<IndustrialState>(IndustrialState.Loading)

    // Публічний незмінний StateFlow для спостереження з боку Composable UI
    val uiState: StateFlow<IndustrialState> = _uiState.asStateFlow()

    init {
        // Початкове завантаження показників при створенні ViewModel
        refreshParams()
    }

    /**
     * Симулює асинхронне зчитування та розрахунок телеметричних даних датчиків установки.
     * Запускає корутину у viewModelScope, переводить стан у IndustrialState.Loading,
     * робить затримку та генерує показники.
     */
    fun refreshParams() {
        viewModelScope.launch {
            _uiState.value = IndustrialState.Loading

            // Імітація затримки зчитування з промислових датчиків по Modbus/Profibus
            delay(1200)

            // Генерація випадкових параметрів для демонстрації діагностики
            val isEmergencySimulated = Random.nextInt(100) < 25 // 25% ймовірність аварійного стану
            val voltage = if (isEmergencySimulated) Random.nextDouble(405.0, 440.0) else Random.nextDouble(370.0, 395.0)
            val current = Random.nextDouble(15.0, 85.0)
            val power = (voltage * current * 1.732 * 0.85) / 1000.0 // P = U * I * sqrt(3) * cos(phi) в кВт
            val temperature = if (isEmergencySimulated && voltage > 400) Random.nextDouble(91.0, 115.0) else Random.nextDouble(45.0, 78.0)

            val params = ElectricalParams(
                voltage = voltage,
                current = current,
                power = power,
                temperature = temperature
            )

            // Перевірка на критичне перевищення норм (напруга > 400 В або температура > 90 °C)
            if (voltage > 400.0 || temperature > 90.0) {
                val reason = when {
                    voltage > 400.0 && temperature > 90.0 -> String.format(Locale.US, "Критична перенапруга (%.1f В) та перегрів (%.1f °C)!", voltage, temperature)
                    voltage > 400.0 -> String.format(Locale.US, "Спрацював захист від перенапруги мережі (%.1f В > 400 В)!", voltage)
                    else -> String.format(Locale.US, "Критичний перегрів установки (%.1f °C > 90 °C)!", temperature)
                }
                _uiState.value = IndustrialState.Error("Критичне перевантаження установки! $reason")
            } else {
                _uiState.value = IndustrialState.Success(
                    params = params,
                    statusMessage = "Обладнання працює в штатному режимі"
                )
            }
        }
    }
}
