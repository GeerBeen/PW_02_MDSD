package ua.kpi.industrial_mvvm.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = IndustrialNavy,
    onPrimary = IndustrialSurface,
    secondary = IndustrialTeal,
    onSecondary = IndustrialSurface,
    background = IndustrialBackground,
    surface = IndustrialSurface,
    onBackground = IndustrialOnSurface,
    onSurface = IndustrialOnSurface,
    error = IndustrialRed
)

private val DarkColorScheme = darkColorScheme(
    primary = IndustrialTeal,
    onPrimary = IndustrialNavy,
    secondary = IndustrialAmber,
    onSecondary = IndustrialNavy,
    error = IndustrialRed
)

@Composable
fun IndustrialMvvmTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
