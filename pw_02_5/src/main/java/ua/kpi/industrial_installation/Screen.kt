package ua.kpi.industrial_installation

import android.net.Uri

/**
 * Ієрархія маршрутів екранів застосунку «Промислова електроустановка».
 * Використовується sealed class для забезпечення безпеки типів під час навігації.
 *
 * @property route Рядок маршруту для NavHost.
 */
sealed class Screen(val route: String) {

    /**
     * Головне меню застосунку.
     */
    data object MainMenu : Screen("main_menu")

    /**
     * Екран введення інформації про оператора установки.
     */
    data object OperatorInfo : Screen("operator_info")

    /**
     * Екран моніторингу та введення параметрів установки.
     * Маршрут приймає параметр operatorName типу String.
     */
    data object Monitoring : Screen("monitoring/{operatorName}") {
        /**
         * Формує конкретний шлях для навігації до екрана моніторингу.
         *
         * @param operatorName Ім'я оператора установки.
         */
        fun createRoute(operatorName: String): String {
            val encodedName = Uri.encode(operatorName.ifBlank { "Operator" })
            return "monitoring/$encodedName"
        }
    }

    /**
     * Екран деталізації розрахунку навантаження електроустановки.
     * Маршрут приймає кількість агрегатів unitsCount (Int)
     * та потужність одного агрегату unitPower (Float).
     */
    data object Details : Screen("details/{unitsCount}/{unitPower}") {
        /**
         * Формує конкретний шлях для навігації до екрана деталізації розрахунку.
         *
         * @param unitsCount Кількість працюючих агрегатів.
         * @param unitPower Потужність одного агрегату в кВт.
         */
        fun createRoute(unitsCount: Int, unitPower: Float): String {
            return "details/$unitsCount/$unitPower"
        }
    }
}
