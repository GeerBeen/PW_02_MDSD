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
 * Поле введення корпоративної електронної пошти інженера або диспетчера.
 *
 * @param value Поточне значення текстового поля.
 * @param isError Прапорець наявності помилки валідації формату email.
 * @param onValueChange Зворотний виклик при зміні вмісту поля.
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun OperatorEmailField(
    value: TextFieldValue,
    isError: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        label = { Text("Email інженера / диспетчера") },
        placeholder = { Text("engineer@industrial.ua") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        supportingText = {
            if (isError) {
                Text("Введіть коректну адресу email (повинна містити '@' та '.')")
            } else {
                Text("Сюди надходитимуть сповіщення про аварійні режими")
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}
