package uk.jacobw.commute.feature.route

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import realtimecommute.composeapp.generated.resources.Res
import realtimecommute.composeapp.generated.resources.compare_arrows_icon
import realtimecommute.composeapp.generated.resources.dash_formatted
import realtimecommute.composeapp.generated.resources.route_no_trains
import realtimecommute.composeapp.generated.resources.route_reverse_desc
import uk.jacobw.commute.data.model.Destination
import uk.jacobw.commute.data.model.Location
import uk.jacobw.commute.data.model.Route
import uk.jacobw.commute.data.model.Service
import uk.jacobw.commute.data.model.Station
import uk.jacobw.commute.feature.shared.AppBar
import uk.jacobw.commute.feature.shared.LoadingSpinner
import uk.jacobw.commute.feature.shared.PlatformIndicator
import uk.jacobw.commute.feature.shared.correctionString
import uk.jacobw.commute.feature.shared.timestamp

@Composable
fun RouteLayout(
    route: Route,
    services: List<Service>,
    isLoadingServices: Boolean,
    onClickNavigationIcon: () -> Unit,
    onClickReverseRoute: () -> Unit,
    onClickService: (Service) -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(Res.string.dash_formatted, route.origin.crsCode, route.destination.crsCode),
                onClickNavigationIcon = onClickNavigationIcon,
                actions = {
                    IconButton(
                        onClick = onClickReverseRoute,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.compare_arrows_icon),
                            contentDescription = stringResource(Res.string.route_reverse_desc),
                        )
                    }
                },
            )
        },
    ) { internalPadding ->
        Column(
            modifier =
                Modifier
                    .padding(internalPadding)
                    .fillMaxWidth(),
        ) {
            RouteCard(route)

            if (isLoadingServices) {
                LoadingSpinner()
            } else {
                ServiceList(services, onClickService)
            }
        }
    }
}

@Composable
private fun RouteCard(route: Route) {
    ElevatedCard(
        modifier =
            Modifier
                .padding(8.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            StationTitleSection(
                station = route.origin,
                modifier = Modifier.align(Alignment.CenterStart),
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
            )
            StationTitleSection(
                station = route.destination,
                modifier = Modifier.align(Alignment.CenterEnd),
            )
        }
    }
}

@Composable
private fun StationTitleSection(
    station: Station,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth(0.4f),
    ) {
        Text(
            text = station.crsCode,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(station.name)
    }
}

@Composable
private fun ServiceList(
    services: List<Service>,
    onClickService: (Service) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (services.isEmpty()) {
            Text(
                text = stringResource(Res.string.route_no_trains),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp),
            )
        }

        services.forEach {
            OutlinedCard(
                onClick = { onClickService(it) },
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                Row(
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier.weight(0.85f),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            val plannedDeparture = it.location.plannedDeparture ?: "Unknown"
                            val realtimeDeparture = it.location.realtimeDeparture ?: "Unknown"

                            if (plannedDeparture == realtimeDeparture) {
                                Text(
                                    text = plannedDeparture.timestamp(),
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            } else {
                                Text(
                                    text = correctionString(plannedDeparture, realtimeDeparture),
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            }

                            Text(
                                text = it.location.destinations.joinToString("/") { it.description },
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }

                        Text(it.operator)
                    }

                    PlatformIndicator(it.location.platformState)
                }
            }
        }
    }
}

@Preview
@Composable
private fun RouteLayoutPreview() {
    MaterialTheme {
        RouteLayout(
            route =
                Route(
                    origin =
                        Station(
                            name = "Farringdon",
                            crsCode = "ZFD",
                        ),
                    destination =
                        Station(
                            name = "London Paddington",
                            crsCode = "PAD",
                        ),
                ),
            services =
                listOf(
                    Service(
                        location =
                            Location(
                                destinations =
                                    listOf(
                                        Destination(description = "Destination"),
                                    ),
                                crs = "",
                                description = "",
                            ),
                        serviceUid = "",
                        operator = "Train Company",
                        _runDate = "2025-05-19",
                    ),
                ),
            isLoadingServices = false,
            onClickNavigationIcon = { },
            onClickReverseRoute = { },
            onClickService = { },
        )
    }
}
