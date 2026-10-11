package ua.kpi.industrial_installation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.kpi.industrial_installation.components.InfoRow
import ua.kpi.industrial_installation.components.MenuButton
import ua.kpi.industrial_installation.components.ScreenTitle
import java.util.Locale

/**
 * Екран «Деталі розрахунку».
 *
 * Обчислює підсумкову споживану потужність електроустановки: Total = unitsCount * unitPower.
 * Відображає вихідні дані та результат за допомогою компонента InfoRow.
 *
 * @param unitsCount Кількість працюючих агрегатів.
 * @param unitPower Потужність одного агрегату (кВт).
 * @param onNavigateBackToMonitoring Обробник повернення на екран моніторингу.
 * @param onNavigateToMainMenu Обробник повернення в головне меню.
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun DetailsScreen(
    unitsCount: Int,
    unitPower: Float,
    onNavigateBackToMonitoring: () -> Unit,
    onNavigateToMainMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Розрахунок загальної потужності установки
    val totalPower = unitsCount * unitPower
    val formattedTotalPower = String.format(Locale.US, "%.2f", totalPower)
    val formattedUnitPower = String.format(Locale.US, "%.2f", unitPower)

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ScreenTitle(title = "Деталі розрахунку")

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Результати розрахунку навантаження",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    InfoRow(
                        label = "Кількість агрегатів:",
                        value = "$unitsCount шт."
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    InfoRow(
                        label = "Потужність 1 агрегату:",
                        value = "$formattedUnitPower кВт"
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    InfoRow(
                        label = "Загальна потужність:",
                        value = "$formattedTotalPower кВт"
                    )
                }
            }

            MenuButton(
                text = "Назад до моніторингу",
                onClick = onNavigateBackToMonitoring
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onNavigateToMainMenu,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Повернутися в меню")
            }
        }
    }
}
