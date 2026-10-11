package ua.kpi.industrial_room.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = IndustrialPrimary,
    onPrimary = IndustrialOnPrimary,
    primaryContainer = IndustrialPrimaryContainer,
    onPrimaryContainer = IndustrialOnPrimaryContainer,
    secondary = IndustrialSecondary,
    onSecondary = IndustrialOnSecondary,
    background = IndustrialBackground,
    surface = IndustrialSurface,
    onBackground = IndustrialOnBackground,
    onSurface = IndustrialOnSurface,
    error = IndustrialError,
)

private val DarkColorScheme = darkColorScheme(
    primary = IndustrialSecondary,
    onPrimary = IndustrialOnPrimary,
    primaryContainer = IndustrialPrimary,
    onPrimaryContainer = IndustrialPrimaryContainer,
    secondary = IndustrialAccent,
    onSecondary = IndustrialOnPrimary,
    error = IndustrialError,
)

@Composable
fun IndustrialRoomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
