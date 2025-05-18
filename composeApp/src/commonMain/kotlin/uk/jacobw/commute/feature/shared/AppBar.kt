package uk.jacobw.commute.feature.shared

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import realtimecommute.composeapp.generated.resources.Res
import realtimecommute.composeapp.generated.resources.navigate_back_desc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    onClickNavigationIcon: (() -> Unit)?,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon =
            onClickNavigationIcon?.let {
                {
                    IconButton(
                        onClick = onClickNavigationIcon,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.navigate_back_desc),
                        )
                    }
                }
            } ?: { },
        actions = actions,
    )
}
