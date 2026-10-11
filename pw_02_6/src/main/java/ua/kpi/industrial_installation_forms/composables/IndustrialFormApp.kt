package ua.kpi.industrial_installation_forms.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.kpi.industrial_installation_forms.components.FormHeader
import ua.kpi.industrial_installation_forms.components.InstallationNameField
import ua.kpi.industrial_installation_forms.components.LoadingIndicator
import ua.kpi.industrial_installation_forms.components.OperatorEmailField
import ua.kpi.industrial_installation_forms.components.PasswordField
import ua.kpi.industrial_installation_forms.components.PowerCapacityField
import ua.kpi.industrial_installation_forms.components.SubmitButton

/**
 * Головний Composable-компонент форми введення даних промислової установки.
 *
 * Управляє станом полів (TextFieldValue), прапорцями помилок валідації,
 * станами завантаження (isLoading) та успішного завершення (isSubmitted).
 * Забезпечує адаптивну верстку (Row для широких екранів, Column для вузьких).
 *
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun IndustrialFormApp(
    modifier: Modifier = Modifier
) {
    // 1. Управління станом полів форми
    var installationName by remember { mutableStateOf(TextFieldValue("")) }
    var operatorEmail by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var powerCapacity by remember { mutableStateOf(TextFieldValue("")) }

    // 2. Окремі прапорці помилок валідації
    var stationNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var powerError by remember { mutableStateOf(false) }

    // 3. Стани обробки та відправки форми
    var isLoading by remember { mutableStateOf(false) }
    var isSubmitted by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
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
            FormHeader()

            Spacer(modifier = Modifier.height(16.dp))

            // Карточка із формою введення даних
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Адаптивна верстка за допомогою BoxWithConstraints
                    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                        if (maxWidth > 600.dp) {
                            // Широкі екрани (планшети/альбомна орієнтація): поля групуються у Row
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                InstallationNameField(
                                    value = installationName,
                                    isError = stationNameError,
                                    onValueChange = {
                                        installationName = it
                                        stationNameError = false
                                    }
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    OperatorEmailField(
                                        value = operatorEmail,
                                        isError = emailError,
                                        onValueChange = {
                                            operatorEmail = it
                                            emailError = false
                                        },
                                        modifier = Modifier.weight(1f)
                                    )

                                    PasswordField(
                                        value = password,
                                        isError = passwordError,
                                        onValueChange = {
                                            password = it
                                            passwordError = false
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                PowerCapacityField(
                                    value = powerCapacity,
                                    isError = powerError,
                                    onValueChange = {
                                        powerCapacity = it
                                        powerError = false
                                    }
                                )
                            }
                        } else {
                            // Стандартний вертикальний макет для смартфонів
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                InstallationNameField(
                                    value = installationName,
                                    isError = stationNameError,
                                    onValueChange = {
                                        installationName = it
                                        stationNameError = false
                                    }
                                )

                                OperatorEmailField(
                                    value = operatorEmail,
                                    isError = emailError,
                                    onValueChange = {
                                        operatorEmail = it
                                        emailError = false
                                    }
                                )

                                PasswordField(
                                    value = password,
                                    isError = passwordError,
                                    onValueChange = {
                                        password = it
                                        passwordError = false
                                    }
                                )

                                PowerCapacityField(
                                    value = powerCapacity,
                                    isError = powerError,
                                    onValueChange = {
                                        powerCapacity = it
                                        powerError = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Відображення індикатора завантаження або кнопки відправки
                    if (isLoading) {
                        LoadingIndicator()
                    } else {
                        SubmitButton(
                            onSubmit = {
                                // 4. Логіка валідації полів форми
                                val nameValid = installationName.text.isNotBlank()
                                val emailText = operatorEmail.text.trim()
                                val emailValid = emailText.contains("@") && emailText.contains(".")
                                val passText = password.text
                                val passwordValid = passText.length >= 8 &&
                                        passText.any { it.isUpperCase() } &&
                                        passText.any { it.isDigit() }

                                val powerVal = powerCapacity.text.trim().toDoubleOrNull()
                                val powerValid = powerVal != null && powerVal > 0.0

                                stationNameError = !nameValid
                                emailError = !emailValid
                                passwordError = !passwordValid
                                powerError = !powerValid

                                // Якщо всі дані пройшли перевірку
                                if (nameValid && emailValid && passwordValid && powerValid) {
                                    coroutineScope.launch {
                                        isLoading = true
                                        isSubmitted = false
                                        delay(2000) // Імітація мережевої затримки відправки даних
                                        isLoading = false
                                        isSubmitted = true
                                    }
                                }
                            },
                            enabled = !isLoading
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Повідомлення про успішну реєстрацію/збереження
            AnimatedVisibility(
                visible = isSubmitted,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Успіх",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Налаштування успішно збережено!",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Установка '${installationName.text}' (потужність: ${powerCapacity.text} кВт) зареєстрована за інженером ${operatorEmail.text}.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedButton(
                            onClick = {
                                installationName = TextFieldValue("")
                                operatorEmail = TextFieldValue("")
                                password = TextFieldValue("")
                                powerCapacity = TextFieldValue("")
                                isSubmitted = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Заповнити форму знову")
                        }
                    }
                }
            }
        }
    }
}
