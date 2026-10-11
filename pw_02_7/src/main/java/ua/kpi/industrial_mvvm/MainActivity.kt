package ua.kpi.industrial_mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ua.kpi.industrial_mvvm.ui.IndustrialScreen
import ua.kpi.industrial_mvvm.ui.theme.IndustrialMvvmTheme

/**
 * Головна Activity для модуля MVVM застосунку «Промислова електроустановка».
 * Ініціалізує тему IndustrialMvvmTheme та запускає IndustrialScreen.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IndustrialMvvmTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IndustrialScreen()
                }
            }
        }
    }
}
