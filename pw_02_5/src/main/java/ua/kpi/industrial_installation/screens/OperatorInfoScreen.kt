package ua.kpi.industrial_installation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
 * Екран «Інформація про оператора».
 *
 * Дозволяє ввести ім'я оператора та номер цеху/зміни.
 * Стан полів зберігається за допомогою rememberSaveable при зміні конфігурації.
 *
 * @param onNavigateToMonitoring Обробник переходу до моніторингу з передачею введеного імені оператора.
 * @param onNavigateToMainMenu Обробник повернення в головне меню.
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun OperatorInfoScreen(
    onNavigateToMonitoring: (operatorName: String) -> Unit,
    onNavigateToMainMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Збереження стану полів введення
    var operatorName by rememberSaveable { mutableStateOf("") }
    var shopShiftNumber by rememberSaveable { mutableStateOf("") }

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
            ScreenTitle(title = "Дані оператора")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = operatorName,
                onValueChange = { operatorName = it },
                label = { Text("Ім'я оператора") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = shopShiftNumber,
                onValueChange = { shopShiftNumber = it },
                label = { Text("Номер цеху / зміни") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            MenuButton(
                text = "Перейти до моніторингу",
                onClick = {
                    val nameToSend = operatorName.ifBlank { "Operator" }
                    onNavigateToMonitoring(nameToSend)
                }
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
