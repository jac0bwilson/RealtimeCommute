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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import realtimecommute.composeapp.generated.resources.Res
import realtimecommute.composeapp.generated.resources.compare_arrows_icon
import realtimecommute.composeapp.generated.resources.dash_formatted
import realtimecommute.composeapp.generated.resources.navigate_back_desc
import realtimecommute.composeapp.generated.resources.route_no_trains
import realtimecommute.composeapp.generated.resources.route_platform_changed
import realtimecommute.composeapp.generated.resources.route_platform_confirmed
import realtimecommute.composeapp.generated.resources.route_platform_estimated
import realtimecommute.composeapp.generated.resources.route_reverse_desc
import uk.jacobw.commute.data.database.RouteWithStations
import uk.jacobw.commute.data.model.Service
import uk.jacobw.commute.feature.LoadingSpinner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteLayout(
    route: RouteWithStations,
    services: List<Service>,
    isLoadingServices: Boolean,
    onClickNavigationIcon: () -> Unit,
    onClickReverseRoute: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.dash_formatted, route.originStation.crsCode, route.destinationStation.crsCode)) },
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
                ServiceList(services)
            }
        }
    }
}

@Composable
private fun RouteCard(route: RouteWithStations) {
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
                    text = route.originStation.crsCode,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(route.originStation.name)
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
                    text = route.destinationStation.crsCode,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(route.destinationStation.name)
            }
        }
    }
}

@Composable
private fun ServiceList(services: List<Service>) {
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
                        if (it.detail.plannedDeparture == it.detail.realtimeDeparture) {
                            Text(
                                text = it.detail.plannedDeparture.timestamp(),
                                style = MaterialTheme.typography.titleLarge,
                            )
                        } else {
                            Text(
                                text = correctionString(it.detail.plannedDeparture, it.detail.realtimeDeparture),
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }

                        Text(
                            text = it.detail.destinations.joinToString("/") { it.description },
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }

                    Text(
                        stringResource(
                            when {
                                it.detail.platformChanged -> Res.string.route_platform_changed
                                it.detail.platformConfirmed -> Res.string.route_platform_confirmed
                                else -> Res.string.route_platform_estimated
                            },
                            it.detail.platform,
                        ),
                    )
                    Text(it.operator)
                }
            }
        }
    }
}

private fun correctionString(
    intended: String,
    actual: String,
): AnnotatedString =
    buildAnnotatedString {
        withStyle(SpanStyle(color = Color.Red, textDecoration = TextDecoration.LineThrough)) {
            append(intended.timestamp())
        }
        append(" ${actual.timestamp()}")
    }

private fun String.timestamp(): String {
    if (this.all { char -> char.isDigit() } && this.length == 4) {
        return this.substring(0, 2) + ":" + this.substring(2)
    }

    return this
}
