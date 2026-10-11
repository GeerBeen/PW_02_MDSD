package ua.kpi.industrial_installation.ui.theme

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
    onSurface = IndustrialOnSurface
)

private val DarkColorScheme = darkColorScheme(
    primary = IndustrialSecondary,
    onPrimary = IndustrialOnPrimary,
    primaryContainer = IndustrialPrimary,
    onPrimaryContainer = IndustrialPrimaryContainer,
    secondary = IndustrialAccent,
    onSecondary = IndustrialOnPrimary
)

/**
 * Тема застосунку «Промислова електроустановка».
 *
 * @param darkTheme Прапорець використання темної теми.
 * @param content Вміст Composable.
 */
@Composable
fun IndustrialInstallationTheme(
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
