package uk.jacobw.commute.feature.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import uk.jacobw.commute.data.model.Platform
import uk.jacobw.commute.data.model.PlatformStatus

@Composable
fun PlatformIndicator(platform: Platform) {
    ElevatedSuggestionChip(
        onClick = { },
        label = { Text(platform.value) },
        icon =
            when (platform.status) {
                PlatformStatus.CHANGED -> {
                    {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                }
                PlatformStatus.CONFIRMED -> {
                    {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = null,
                        )
                    }
                }
                else -> null
            },
        enabled = false,
    )
}
