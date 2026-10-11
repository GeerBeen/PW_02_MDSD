package ua.kpi.industrial_installation_forms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

/**
 * Поле введення назви промислової установки.
 *
 * @param value Поточне значення текстового поля.
 * @param isError Прапорець наявності помилки валідації.
 * @param onValueChange Зворотний виклик при зміні вмісту поля.
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun InstallationNameField(
    value: TextFieldValue,
    isError: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        label = { Text("Назва установки") },
        placeholder = { Text("напр. ТП-1000 кВА Цех №3") },
        singleLine = true,
        supportingText = {
            if (isError) {
                Text("Назва установки не може бути порожньою")
            } else {
                Text("Вкажіть унікальну назву або інвентарний номер")
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}
