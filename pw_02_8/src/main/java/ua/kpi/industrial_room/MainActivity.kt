package ua.kpi.industrial_room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.room.Room
import ua.kpi.industrial_room.data.IndustrialDatabase
import ua.kpi.industrial_room.repository.IndustrialRepository
import ua.kpi.industrial_room.ui.IndustrialRoomApp
import ua.kpi.industrial_room.ui.theme.IndustrialRoomTheme
import ua.kpi.industrial_room.viewmodel.IndustrialViewModel
import ua.kpi.industrial_room.viewmodel.IndustrialViewModelFactory

/**
 * Головна Activity для ініціалізації БД Room, репозиторію, ViewModel та запуску UI.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ініціалізація Room БД
        val db = Room.databaseBuilder(
            applicationContext,
            IndustrialDatabase::class.java,
            "industrial_database"
        ).build()

        // Створення репозиторію
        val repository = IndustrialRepository(db.industrialDao())

        // Створення ViewModel з використанням фабрики
        val viewModel: IndustrialViewModel by viewModels {
            IndustrialViewModelFactory(repository)
        }

        setContent {
            IndustrialRoomTheme {
                IndustrialRoomApp(viewModel = viewModel)
            }
        }
    }
}
