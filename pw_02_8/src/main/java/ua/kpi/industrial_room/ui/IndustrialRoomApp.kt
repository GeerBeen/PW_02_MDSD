package ua.kpi.industrial_room.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ua.kpi.industrial_room.viewmodel.IndustrialViewModel

/**
 * Головний Composable екран додатку з підтримкою вертикального скролу.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IndustrialRoomApp(viewModel: IndustrialViewModel) {
    val stations by viewModel.stations.collectAsState()
    val selectedStationId by viewModel.selectedStationId.collectAsState()
    val minPowerFilter by viewModel.minPowerFilter.collectAsState()
    val measurements by viewModel.measurements.collectAsState()

    var stationNameInput by remember { mutableStateOf("") }
    var powerInput by remember { mutableStateOf("") }
    var filterInput by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Промислова електроустановка (Room БД)",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider()

            // 1. Форма додавання установки
            Text(
                text = "1. Додавання установки",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
                value = stationNameInput,
                onValueChange = { stationNameInput = it },
                label = { Text("Назва установки") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Button(
                onClick = {
                    if (stationNameInput.isNotBlank()) {
                        viewModel.addStation(stationNameInput)
                        stationNameInput = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Додати установку")
            }

            HorizontalDivider()

            // 2. Вибір установки
            Text(
                text = "2. Вибір активної установки",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            if (stations.isEmpty()) {
                Text(
                    text = "Список установок порожній. Додайте першу установку.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    stations.forEach { station ->
                        val isSelected = station.id == selectedStationId
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            ),
                            modifier = Modifier.clickable {
                                viewModel.selectStation(station.id)
                            }
                        ) {
                            Text(
                                text = station.name + if (isSelected) " ✓" else "",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                    }
                }
            }

            // 3 & 4. Форми та список вимірювань для обраної установки
            if (selectedStationId != null) {
                HorizontalDivider()

                val currentStation = stations.find { it.id == selectedStationId }
                Text(
                    text = "3. Вимірювання для установки: ${currentStation?.name ?: ""}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                // Форма додавання вимірювання
                OutlinedTextField(
                    value = powerInput,
                    onValueChange = { powerInput = it },
                    label = { Text("Потужність (кВт)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Button(
                    onClick = {
                        val power = powerInput.toDoubleOrNull()
                        val currentStationId = selectedStationId
                        if (power != null && currentStationId != null) {
                            viewModel.addMeasurement(currentStationId, power)
                            powerInput = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Додати замір")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Поле для фільтрації
                OutlinedTextField(
                    value = filterInput,
                    onValueChange = { input ->
                        filterInput = input
                        val minVal = input.toDoubleOrNull() ?: 0.0
                        viewModel.setFilter(minVal)
                    },
                    label = { Text("Мін. потужність (фільтр)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Text(
                    text = "Поточний поріг фільтрації: $minPowerFilter кВт",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Список вимірювань
                Text(
                    text = "4. Список замірів (${measurements.size}):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )

                if (measurements.isEmpty()) {
                    Text(
                        text = "Вимірювань не знайдено за заданим фільтром.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        measurements.forEach { measurement ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Потужність: ${measurement.powerKw} кВт",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    IconButton(
                                        onClick = { viewModel.deleteMeasurement(measurement) }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Видалити",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
