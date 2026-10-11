package ua.kpi.industrial_mvvm.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ElectricMeter
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ua.kpi.industrial_mvvm.data.IndustrialState
import ua.kpi.industrial_mvvm.ui.components.ParamCard
import ua.kpi.industrial_mvvm.ui.components.StatusBadge
import ua.kpi.industrial_mvvm.ui.theme.IndustrialRed
import ua.kpi.industrial_mvvm.viewmodel.IndustrialViewModel
import java.util.Locale

/**
 * Головний Composable-екран системи діагностики та моніторингу електроустановки.
 *
 * Підписується на потік стану IndustrialViewModel.uiState за допомогою collectAsState.
 * Обробляє три стани UI: Loading, Error, Success.
 *
 * @param viewModel Екземпляр IndustrialViewModel, що зберігає стан та бізнес-логіку.
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun IndustrialScreen(
    viewModel: IndustrialViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // Реактивна підписка на StateFlow з ViewModel
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Моніторинг промислової установки",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Плавне переключення між станами Loading, Success та Error
            Crossfade(targetState = state, label = "StateTransition") { currentState ->
                when (currentState) {
                    is IndustrialState.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Зчитування датчиків...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    is IndustrialState.Error -> {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = IndustrialRed.copy(alpha = 0.1f)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = "Аварія",
                                    tint = IndustrialRed,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "КРИТИЧНА АВАРІЯ",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = IndustrialRed
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = currentState.errorMessage,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Button(
                                    onClick = { viewModel.refreshParams() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = IndustrialRed
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Refresh,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Скинути аварію / Перезапустити")
                                }
                            }
                        }
                    }

                    is IndustrialState.Success -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            StatusBadge(
                                isError = false,
                                text = currentState.statusMessage,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            val params = currentState.params

                            // Адаптивне відображення карток параметрів у 1 або 2 колонки
                            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                                if (maxWidth > 600.dp) {
                                    // 2 колонки для широких екранів
                                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            ParamCard(
                                                label = "Напруга мережі",
                                                value = String.format(Locale.US, "%.1f", params.voltage),
                                                unit = "В",
                                                icon = Icons.Filled.Bolt,
                                                modifier = Modifier.weight(1f)
                                            )
                                            ParamCard(
                                                label = "Споживаний струм",
                                                value = String.format(Locale.US, "%.1f", params.current),
                                                unit = "А",
                                                icon = Icons.Filled.ElectricMeter,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            ParamCard(
                                                label = "Активна потужність",
                                                value = String.format(Locale.US, "%.2f", params.power),
                                                unit = "кВт",
                                                icon = Icons.Filled.Power,
                                                modifier = Modifier.weight(1f)
                                            )
                                            ParamCard(
                                                label = "Температура обмоток",
                                                value = String.format(Locale.US, "%.1f", params.temperature),
                                                unit = "°C",
                                                icon = Icons.Filled.Thermostat,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                } else {
                                    // 1 колонка для вузьких екранів (смартфонів)
                                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                        ParamCard(
                                            label = "Напруга мережі",
                                            value = String.format(Locale.US, "%.1f", params.voltage),
                                            unit = "В",
                                            icon = Icons.Filled.Bolt
                                        )
                                        ParamCard(
                                            label = "Споживаний струм",
                                            value = String.format(Locale.US, "%.1f", params.current),
                                            unit = "А",
                                            icon = Icons.Filled.ElectricMeter
                                        )
                                        ParamCard(
                                            label = "Активна потужність",
                                            value = String.format(Locale.US, "%.2f", params.power),
                                            unit = "кВт",
                                            icon = Icons.Filled.Power
                                        )
                                        ParamCard(
                                            label = "Температура обмоток",
                                            value = String.format(Locale.US, "%.1f", params.temperature),
                                            unit = "°C",
                                            icon = Icons.Filled.Thermostat
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            OutlinedButton(
                                onClick = { viewModel.refreshParams() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Оновити показники")
                            }
                        }
                    }
                }
            }
        }
    }
}
