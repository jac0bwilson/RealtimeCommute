package uk.jacobw.commute.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RouteScreen(
    viewModel: RouteViewModel = koinViewModel(),
    onNavigationIconPressed: () -> Unit,
) {
    val route by viewModel.route.collectAsStateWithLifecycle()

    RouteLayout(
        route = route!!,
        onNavigationIconPressed = onNavigationIconPressed,
    )
}