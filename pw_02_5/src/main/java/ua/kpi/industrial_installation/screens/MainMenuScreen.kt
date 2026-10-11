package ua.kpi.industrial_installation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.kpi.industrial_installation.components.MenuButton
import ua.kpi.industrial_installation.components.ScreenTitle

/**
 * Екран «Головне меню».
 *
 * Містить назву електроустановки та варіанти переходу:
 * 1. На екран інформації про оператора.
 * 2. На екран моніторингу за замовчуванням (з дефолтним оператором "Operator").
 *
 * Верстка є адаптивною (з використанням BoxWithConstraints):
 * якщо maxWidth > 600.dp, кнопки розташовуються в Row, інакше в Column.
 *
 * @param onNavigateToOperatorInfo Обробник переходу на екран інформації про оператора.
 * @param onNavigateToMonitoring Обробник переходу на екран моніторингу із зазначеним ім'ям оператора.
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun MainMenuScreen(
    onNavigateToOperatorInfo: () -> Unit,
    onNavigateToMonitoring: (operatorName: String) -> Unit,
    modifier: Modifier = Modifier
) {
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
            ScreenTitle(title = "Промислова електроустановка")

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = "Система керування та контролю електроенергетичних параметрів промислової установки",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Адаптивне розміщення елементів управління залежно від ширини екрана
            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (maxWidth > 600.dp) {
                    // Горизонтальне розміщення кнопок для широких екранів (планшети, горизонтальна орієнтація)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MenuButton(
                            text = "Інформація про оператора",
                            onClick = onNavigateToOperatorInfo,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        MenuButton(
                            text = "Моніторинг установки",
                            onClick = { onNavigateToMonitoring("Operator") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else {
                    // Вертикальне розміщення кнопок для стандартних екранів (смартфони)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MenuButton(
                            text = "Інформація про оператора",
                            onClick = onNavigateToOperatorInfo
                        )
                        MenuButton(
                            text = "Моніторинг установки",
                            onClick = { onNavigateToMonitoring("Operator") }
                        )
                    }
                }
            }
        }
    }
}
