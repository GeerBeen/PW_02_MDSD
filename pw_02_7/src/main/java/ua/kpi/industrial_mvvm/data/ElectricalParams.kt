package ua.kpi.industrial_mvvm.data

/**
 * Data-клас для збереження поточних електричних та теплових параметрів промислової установки.
 *
 * @property voltage Поточна напруга мережі живлення в Вольтах (В).
 * @property current Споживаний струм в Амперах (А).
 * @property power Активна потужність установки в кіловатах (кВт).
 * @property temperature Температура обмоток/корпусу установки в градусах Цельсія (°C).
 */
data class ElectricalParams(
    val voltage: Double,
    val current: Double,
    val power: Double,
    val temperature: Double
)
