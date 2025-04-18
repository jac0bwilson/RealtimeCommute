package uk.jacobw.commute

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.HOME.name
        ) {
            featureGraph(navController)
        }
    }
}