package uk.jacobw.commute.feature.service

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import realtimecommute.composeapp.generated.resources.Res
import realtimecommute.composeapp.generated.resources.dash_formatted
import realtimecommute.composeapp.generated.resources.navigate_back_desc
import uk.jacobw.commute.data.database.RouteWithStations
import uk.jacobw.commute.data.model.Location
import uk.jacobw.commute.feature.LoadingSpinner
import uk.jacobw.commute.feature.PlatformText
import uk.jacobw.commute.feature.correctionString
import uk.jacobw.commute.feature.timestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceLayout(
    route: RouteWithStations,
    locationsBeforeRoute: List<Location>,
    locationsOnRoute: List<Location>,
    locationsAfterRoute: List<Location>,
    isLoadingLocations: Boolean,
    onClickNavigationIcon: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(
                            Res.string.dash_formatted,
                            route.originStation.crsCode,
                            route.destinationStation.crsCode,
                        ),
                    )
                },
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
            )
        },
    ) { internalPadding ->
        Column(
            modifier =
                Modifier
                    .padding(internalPadding)
                    .fillMaxWidth(),
        ) {
            if (isLoadingLocations) {
                LoadingSpinner()
            } else {
                LocationLists(locationsBeforeRoute, locationsOnRoute, locationsAfterRoute)
            }
        }
    }
}

@Composable
private fun LocationLists(
    locationsBeforeRoute: List<Location>,
    locationsOnRoute: List<Location>,
    locationsAfterRoute: List<Location>,
) {
    Column(
        modifier =
            Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (locationsBeforeRoute.isNotEmpty()) {
            ExpandingSection(locationsBeforeRoute, "before")
        }

        locationsOnRoute.forEach {
            LocationItem(it)
        }

        if (locationsAfterRoute.isNotEmpty()) {
            ExpandingSection(locationsAfterRoute, "after")
        }
    }
}

@Composable
private fun ExpandingSection(
    locations: List<Location>,
    beforeOrAfter: String,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        onClick = { isExpanded = !isExpanded },
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            Text(
                text = "${if (isExpanded) "Hide" else "Show"} stations $beforeOrAfter your journey",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            AnimatedVisibility(isExpanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    locations.forEach {
                        LocationItem(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationItem(location: Location) {
    val plannedTime = location.plannedDeparture ?: location.plannedArrival ?: "Unknown"
    val actualTime = location.realtimeDeparture ?: location.realtimeArrival ?: "Unknown"

    Column(
        modifier =
            Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (plannedTime == actualTime) {
                Text(
                    text = plannedTime.timestamp(),
                )
            } else {
                Text(
                    text = correctionString(plannedTime, actualTime),
                )
            }
            Text(location.description)
        }

        PlatformText(location)
    }
}
