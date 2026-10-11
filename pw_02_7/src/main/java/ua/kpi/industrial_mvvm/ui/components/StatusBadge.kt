package ua.kpi.industrial_mvvm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ua.kpi.industrial_mvvm.ui.theme.IndustrialGreen
import ua.kpi.industrial_mvvm.ui.theme.IndustrialRed

/**
 * Віджет статусу роботи промислової установки (зелений для норми, червоний для аварії).
 *
 * @param isError Прапорець наявності аварійного стану.
 * @param text Текст стану.
 * @param modifier Модифікатор оформлення.
 */
@Composable
fun StatusBadge(
    isError: Boolean,
    text: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isError) IndustrialRed.copy(alpha = 0.15f) else IndustrialGreen.copy(alpha = 0.15f)
    val contentColor = if (isError) IndustrialRed else IndustrialGreen
    val icon = if (isError) Icons.Filled.Warning else Icons.Filled.CheckCircle

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = contentColor
        )
    }
}
