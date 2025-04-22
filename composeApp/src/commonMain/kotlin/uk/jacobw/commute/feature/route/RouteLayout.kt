package uk.jacobw.commute.feature.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.jacobw.commute.data.database.RouteWithStations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteLayout(
    route: RouteWithStations,
    onNavigationIconPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${route.originStation.crsCode} - ${route.destinationStation.crsCode}") },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigationIconPressed
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            )
        }
    ) { internalPadding ->
        Column(
            modifier = Modifier
                .padding(internalPadding)
                .fillMaxWidth()
        ) {
            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .fillMaxWidth(0.4f),
                    ) {
                        Text(
                            route.originStation.crsCode,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            route.originStation.name
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxWidth(0.4f),
                    ) {
                        Text(
                            route.destinationStation.crsCode,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            route.destinationStation.name
                        )
                    }
                }
            }
        }
    }
}