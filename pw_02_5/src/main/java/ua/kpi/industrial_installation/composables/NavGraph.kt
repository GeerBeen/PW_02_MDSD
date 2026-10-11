package ua.kpi.industrial_installation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ua.kpi.industrial_installation.Screen
import ua.kpi.industrial_installation.screens.DetailsScreen
import ua.kpi.industrial_installation.screens.MainMenuScreen
import ua.kpi.industrial_installation.screens.MonitoringScreen
import ua.kpi.industrial_installation.screens.OperatorInfoScreen

/**
 * Головний навігаційний граф застосунку «Промислова електроустановка».
 *
 * Реєструє 4 екрани та обробляє передачу аргументів між ними:
 * 1. Screen.MainMenu - Головне меню.
 * 2. Screen.OperatorInfo - Форма інформації про оператора.
 * 3. Screen.Monitoring - Моніторинг з аргументом operatorName (StringType).
 * 4. Screen.Details - Деталі розрахунку з аргументами unitsCount (IntType) та unitPower (FloatType).
 *
 * @param navController Контролер навігації Jetpack Navigation.
 * @param modifier Модифікатор зовнішнього вигляду.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainMenu.route,
        modifier = modifier
    ) {
        // Екран 1: Головне меню
        composable(route = Screen.MainMenu.route) {
            MainMenuScreen(
                onNavigateToOperatorInfo = {
                    navController.navigate(Screen.OperatorInfo.route)
                },
                onNavigateToMonitoring = { operatorName ->
                    navController.navigate(Screen.Monitoring.createRoute(operatorName))
                }
            )
        }

        // Екран 2: Інформація про оператора
        composable(route = Screen.OperatorInfo.route) {
            OperatorInfoScreen(
                onNavigateToMonitoring = { operatorName ->
                    navController.navigate(Screen.Monitoring.createRoute(operatorName))
                },
                onNavigateToMainMenu = {
                    navController.navigate(Screen.MainMenu.route) {
                        popUpTo(Screen.MainMenu.route) { inclusive = true }
                    }
                }
            )
        }

        // Екран 3: Моніторинг та налаштування (із параметром operatorName)
        composable(
            route = Screen.Monitoring.route,
            arguments = listOf(
                navArgument("operatorName") {
                    type = NavType.StringType
                    defaultValue = "Operator"
                }
            )
        ) { backStackEntry ->
            val operatorName = backStackEntry.arguments?.getString("operatorName") ?: "Operator"

            MonitoringScreen(
                operatorName = operatorName,
                onNavigateToDetails = { unitsCount, unitPower ->
                    navController.navigate(Screen.Details.createRoute(unitsCount, unitPower))
                },
                onNavigateToMainMenu = {
                    navController.navigate(Screen.MainMenu.route) {
                        popUpTo(Screen.MainMenu.route) { inclusive = true }
                    }
                }
            )
        }

        // Екран 4: Деталі розрахунку (із параметрами unitsCount та unitPower)
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("unitsCount") {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("unitPower") {
                    type = NavType.FloatType
                    defaultValue = 0f
                }
            )
        ) { backStackEntry ->
            val unitsCount = backStackEntry.arguments?.getInt("unitsCount") ?: 0
            val unitPower = backStackEntry.arguments?.getFloat("unitPower") ?: 0f

            DetailsScreen(
                unitsCount = unitsCount,
                unitPower = unitPower,
                onNavigateBackToMonitoring = {
                    navController.popBackStack()
                },
                onNavigateToMainMenu = {
                    navController.navigate(Screen.MainMenu.route) {
                        popUpTo(Screen.MainMenu.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
