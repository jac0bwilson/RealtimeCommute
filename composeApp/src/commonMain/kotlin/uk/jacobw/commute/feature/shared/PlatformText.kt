package uk.jacobw.commute.feature.shared

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import realtimecommute.composeapp.generated.resources.Res
import realtimecommute.composeapp.generated.resources.route_platform_changed
import realtimecommute.composeapp.generated.resources.route_platform_confirmed
import realtimecommute.composeapp.generated.resources.route_platform_estimated
import uk.jacobw.commute.data.model.Location

@Composable
fun PlatformText(location: Location) {
    Text(
        location.platform?.let {
            stringResource(
                when {
                    location.platformChanged -> Res.string.route_platform_changed
                    location.platformConfirmed -> Res.string.route_platform_confirmed
                    else -> Res.string.route_platform_estimated
                },
                it,
            )
        } ?: "Unknown Platform",
    )
}
