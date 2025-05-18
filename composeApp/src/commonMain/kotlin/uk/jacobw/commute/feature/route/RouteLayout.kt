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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import realtimecommute.composeapp.generated.resources.Res
import realtimecommute.composeapp.generated.resources.compare_arrows_icon
import realtimecommute.composeapp.generated.resources.dash_formatted
import realtimecommute.composeapp.generated.resources.navigate_back_desc
import realtimecommute.composeapp.generated.resources.route_no_trains
import realtimecommute.composeapp.generated.resources.route_reverse_desc
import uk.jacobw.commute.data.model.Route
import uk.jacobw.commute.data.model.Service
import uk.jacobw.commute.feature.LoadingSpinner
import uk.jacobw.commute.feature.PlatformText
import uk.jacobw.commute.feature.correctionString
import uk.jacobw.commute.feature.timestamp

@OptIn(ExperimentalMaterial3Api::class)
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
            TopAppBar(
                title = { Text(stringResource(Res.string.dash_formatted, route.origin.crsCode, route.destination.crsCode)) },
                navigationIcon = {
                    IconButton(
                        onClick = onClickNavigationIcon,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.navigate_back_desc),
                        )
                    }
                },
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
            Column(
                modifier =
                    Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxWidth(0.4f),
            ) {
                Text(
                    text = route.origin.crsCode,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(route.origin.name)
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
            )
            Column(
                modifier =
                    Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxWidth(0.4f),
            ) {
                Text(
                    text = route.origin.crsCode,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(route.destination.name)
            }
        }
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
                Column(
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
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

                    PlatformText(it.location)
                    Text(it.operator)
                }
            }
        }
    }
}
