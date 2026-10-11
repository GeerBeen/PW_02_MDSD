package ua.kpi.industrial_installation_forms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Поле введення пароля із маскуванням символів та перемикачем видимості.
 *
 * @param value Поточне значення текстового поля.
 * @param isError Прапорець наявності помилки валідації пароля.
 * @param onValueChange Зворотний виклик при зміні вмісту поля.
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun PasswordField(
    value: TextFieldValue,
    isError: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        label = { Text("Пароль доступу") },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Сховати пароль" else "Показати пароль"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
        supportingText = {
            if (isError) {
                Text("Пароль має містити ≥ 8 символів, хоча б одну велику літеру та цифру")
            } else {
                Text("Вимоги: мін. 8 символів, 1 велика літера, 1 цифра")
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}
