package ua.kpi.industrial_installation_forms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

/**
 * Поле введення встановленої потужності промислової установки в кВт.
 *
 * @param value Поточне значення текстового поля.
 * @param isError Прапорець наявності помилки валідації значення потужності.
 * @param onValueChange Зворотний виклик при зміні вмісту поля.
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun PowerCapacityField(
    value: TextFieldValue,
    isError: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        label = { Text("Потужність установки (кВт)") },
        placeholder = { Text("напр. 250.5") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        supportingText = {
            if (isError) {
                Text("Введіть додатне числове значення потужності (> 0)")
            } else {
                Text("Зазначте сумарну активну потужність у кіловатах")
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}
