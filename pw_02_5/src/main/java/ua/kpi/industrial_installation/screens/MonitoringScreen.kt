package ua.kpi.industrial_installation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ua.kpi.industrial_installation.components.MenuButton
import ua.kpi.industrial_installation.components.ScreenTitle

/**
 * Екран «Моніторинг та налаштування».
 *
 * Відображає привітання оператора та містить поля для введення параметрів
 * електроустановки (кількість агрегатів та потужність кожного).
 *
 * @param operatorName Ім'я оператора, отримане через аргументи навігації.
 * @param onNavigateToDetails Обробник переходу на екран деталізації з передачею розрахованих даних.
 * @param onNavigateToMainMenu Обробник повернення в головне меню.
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun MonitoringScreen(
    operatorName: String,
    onNavigateToDetails: (unitsCount: Int, unitPower: Float) -> Unit,
    onNavigateToMainMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Збереження стану числових полів введення
    var unitsCountText by rememberSaveable { mutableStateOf("4") }
    var unitPowerText by rememberSaveable { mutableStateOf("15.5") }
    var isError by rememberSaveable { mutableStateOf(false) }

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
            ScreenTitle(title = "Моніторинг установки")

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Оператор: $operatorName",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }

            OutlinedTextField(
                value = unitsCountText,
                onValueChange = {
                    unitsCountText = it
                    isError = false
                },
                label = { Text("Кількість працюючих агрегатів (шт.)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = isError,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = unitPowerText,
                onValueChange = {
                    unitPowerText = it
                    isError = false
                },
                label = { Text("Потужність одного агрегату (кВт)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                isError = isError,
                modifier = Modifier.fillMaxWidth()
            )

            if (isError) {
                Text(
                    text = "Будь ласка, введіть коректні числові значення",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            MenuButton(
                text = "Розрахувати навантаження",
                onClick = {
                    val count = unitsCountText.toIntOrNull()
                    val power = unitPowerText.toFloatOrNull()

                    if (count != null && count >= 0 && power != null && power >= 0f) {
                        onNavigateToDetails(count, power)
                    } else {
                        isError = true
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onNavigateToMainMenu,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Головне меню")
            }
        }
    }
}
