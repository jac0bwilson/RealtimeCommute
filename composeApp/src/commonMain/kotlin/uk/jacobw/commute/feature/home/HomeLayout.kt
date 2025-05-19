package uk.jacobw.commute.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import realtimecommute.composeapp.generated.resources.Res
import realtimecommute.composeapp.generated.resources.dash_formatted
import realtimecommute.composeapp.generated.resources.home_delete_desc
import realtimecommute.composeapp.generated.resources.home_input_destination_label
import realtimecommute.composeapp.generated.resources.home_input_origin_label
import realtimecommute.composeapp.generated.resources.home_input_submit_button
import realtimecommute.composeapp.generated.resources.home_input_title
import realtimecommute.composeapp.generated.resources.home_title
import realtimecommute.composeapp.generated.resources.train_icon
import uk.jacobw.commute.data.model.Route
import uk.jacobw.commute.data.model.Station
import uk.jacobw.commute.feature.shared.AppBar

@Composable
fun HomeLayout(
    stationOptions: List<Station>,
    routes: List<Route>,
    addRoute: (String, String) -> Boolean,
    deleteAllRoutes: () -> Unit,
    onNavigateToRoute: (Route) -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(Res.string.home_title),
                onClickNavigationIcon = null,
                actions = {
                    IconButton(
                        onClick = deleteAllRoutes,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(Res.string.home_delete_desc),
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
            JourneyInput(
                stationOptions = stationOptions,
                addRoute = addRoute,
            )

            SavedRoutes(
                routes = routes,
                onNavigateToRoute = onNavigateToRoute,
            )
        }
    }
}

@Composable
private fun SavedRoutes(
    routes: List<Route>,
    onNavigateToRoute: (Route) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        routes.forEach {
            OutlinedCard(
                onClick = { onNavigateToRoute(it) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    StationName(
                        station = it.origin,
                        modifier = Modifier.align(Alignment.CenterStart),
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                    )
                    StationName(
                        station = it.destination,
                        modifier = Modifier.align(Alignment.CenterEnd),
                    )
                }
            }
        }
    }
}

@Composable
private fun StationName(
    station: Station,
    modifier: Modifier = Modifier,
) {
    Text(
        text = station.name,
        modifier =
            modifier
                .fillMaxWidth(0.4f),
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
private fun JourneyInput(
    stationOptions: List<Station>,
    addRoute: (String, String) -> Boolean,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val origin = remember { mutableStateOf("") }
    val destination = remember { mutableStateOf("") }

    fun submit() {
        val success = addRoute(origin.value, destination.value)
        if (success) {
            origin.value = ""
            destination.value = ""
        }
    }

    ElevatedCard(
        modifier =
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(Res.string.home_input_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
            )

            StationInput(
                stationOptions = stationOptions,
                currentValue = origin.value,
                setValue = { origin.value = it },
                label = stringResource(Res.string.home_input_origin_label),
                keyboardImeAction = ImeAction.Next,
            )

            StationInput(
                stationOptions = stationOptions,
                currentValue = destination.value,
                setValue = { destination.value = it },
                label = stringResource(Res.string.home_input_destination_label),
                keyboardImeAction = ImeAction.Done,
                keyboardActions =
                    KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            submit()
                        },
                    ),
            )

            Button(
                onClick = {
                    keyboardController?.hide()
                    submit()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = origin.value.isNotBlank() && destination.value.isNotBlank(),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.train_icon),
                    contentDescription = null,
                )
                Text(stringResource(Res.string.home_input_submit_button))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StationInput(
    stationOptions: List<Station>,
    currentValue: String,
    setValue: (String) -> Unit,
    label: String,
    keyboardImeAction: ImeAction,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    var expanded by remember { mutableStateOf(false) }
    val filteredOptions =
        stationOptions.filter {
            val currentLower = currentValue.lowercase()
            it.name.lowercase().contains(currentLower) || it.crsCode.lowercase().contains(currentLower)
        }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable),
            value = currentValue,
            onValueChange = setValue,
            label = { Text(label) },
            singleLine = true,
            keyboardOptions =
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = keyboardImeAction,
                ),
            keyboardActions = keyboardActions,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                    modifier = Modifier.menuAnchor(MenuAnchorType.SecondaryEditable),
                )
            },
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Box(
                modifier =
                    Modifier
                        .height(250.dp)
                        .width(300.dp),
            ) {
                LazyColumn {
                    items(filteredOptions) { item ->
                        DropdownMenuItem(
                            text = { Text(stringResource(Res.string.dash_formatted, item.crsCode, item.name)) },
                            onClick = {
                                setValue(item.name)
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeLayoutPreview() {
    MaterialTheme {
        HomeLayout(
            stationOptions = emptyList(),
            routes =
                listOf(
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
                ),
            addRoute = { _, _ -> true },
            deleteAllRoutes = { },
            onNavigateToRoute = { },
        )
    }
}
