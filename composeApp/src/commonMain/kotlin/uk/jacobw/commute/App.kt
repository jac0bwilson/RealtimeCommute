package uk.jacobw.commute

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration
import uk.jacobw.commute.di.appModule
import uk.jacobw.commute.di.platformModule

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinMultiplatformApplication(
        config =
            koinConfiguration {
                modules(platformModule, appModule)
            },
    ) {
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
        ) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.HomeScreen,
            ) {
                featureGraph(navController)
            }
        }
    }
}
