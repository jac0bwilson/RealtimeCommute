package uk.jacobw.commute.feature.route

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import realtimecommute.composeapp.generated.resources.Res
import realtimecommute.composeapp.generated.resources.compare_arrows_icon
import uk.jacobw.commute.data.database.RouteWithStations
import uk.jacobw.commute.data.model.Service

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteLayout(
    route: RouteWithStations,
    services: List<Service>,
    onNavigationIconPressed: () -> Unit,
    onReverseRoutePressed: () -> Unit,
    onReloadServices: () -> Unit,
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
                },
                actions = {
                    IconButton(
                        onClick = onReverseRoutePressed
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.compare_arrows_icon),
                            contentDescription = "Reverse route"
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
            RouteCard(route)

            ServiceList(services)
        }
    }
}

@Composable
private fun RouteCard(
    route: RouteWithStations,
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

@Composable
private fun ServiceList(
    services: List<Service>,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        services.forEach {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    if (it.detail.plannedDeparture == it.detail.realtimeDeparture) {
                        Text(
                            it.detail.plannedDeparture,
                            style = MaterialTheme.typography.titleLarge
                        )
                    } else {
                        Text(
                            correctionString(it.detail.plannedDeparture, it.detail.realtimeDeparture),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    if (it.detail.platformChanged) {
                        Text("Now departing from platform ${it.detail.platform}")
                    } else if (it.detail.platformConfirmed) {
                        Text("Departing from platform ${it.detail.platform}")
                    } else {
                        Text("Expected to depart from platform ${it.detail.platform}")
                    }
                    Text(it.operator)
                }
            }
        }
    }
}

private fun correctionString(intended: String, actual: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(SpanStyle(color = Color.Red, textDecoration = TextDecoration.LineThrough)) {
            append(intended)
        }
        append(" $actual")
    }
}