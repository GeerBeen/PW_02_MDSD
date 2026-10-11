package ua.kpi.industrial_installation_forms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Кнопка підтвердження та відправки даних форми на сервер/в систему.
 *
 * @param onSubmit Зворотний виклик при натисканні.
 * @param modifier Модифікатор оформлення.
 * @param enabled Прапорець активності кнопки.
 */
@Composable
fun SubmitButton(
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onSubmit,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = "Зберегти налаштування",
            style = MaterialTheme.typography.titleLarge
        )
    }
}
