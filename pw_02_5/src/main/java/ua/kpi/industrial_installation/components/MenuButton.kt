package ua.kpi.industrial_installation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Перевикористовувана кнопка меню із заповненням всієї доступної ширини та стандартними відступами.
 *
 * @param text Текст, що відображається на кнопці.
 * @param onClick Обробок події натискання на кнопку.
 * @param modifier Модифікатор для налаштування зовнішнього вигляду чи розмірів.
 * @param enabled Прапорець доступності кнопки для взаємодії.
 */
@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
