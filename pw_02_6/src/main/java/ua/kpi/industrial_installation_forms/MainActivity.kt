package ua.kpi.industrial_installation_forms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ua.kpi.industrial_installation_forms.composables.IndustrialFormApp
import ua.kpi.industrial_installation_forms.ui.theme.IndustrialInstallationFormsTheme

/**
 * Головна Activity для модуля форми введення даних застосунку «Промислова електроустановка».
 * Ініціалізує тему IndustrialInstallationFormsTheme та запускає IndustrialFormApp.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IndustrialInstallationFormsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IndustrialFormApp()
                }
            }
        }
    }
}
