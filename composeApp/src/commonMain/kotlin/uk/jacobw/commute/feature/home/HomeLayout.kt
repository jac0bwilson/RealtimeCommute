package uk.jacobw.commute.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uk.jacobw.commute.data.database.RouteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLayout(
    from: String,
    to: String,
    routes: List<RouteEntity>,
    onNavigateToSample: () -> Unit,
    updateFrom: (String) -> Unit,
    updateTo: (String) -> Unit,
    addRoute: () -> Unit,
    deleteAllRoutes: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Realtime Commute") },
                actions = {
                    IconButton(
                        onClick = onNavigateToSample
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Sample code"
                        )
                    }

                    IconButton(
                        onClick = deleteAllRoutes
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete routes"
                        )
                    }
                }
            )
        },
    ) { internalPadding ->
        Column(
            modifier = Modifier
                .padding(internalPadding)
                .fillMaxWidth()
        ) {
            JourneyInput(
                from = from,
                to = to,
                updateFrom = updateFrom,
                updateTo = updateTo,
                addRoute = addRoute,
            )

            SavedRoutes(routes)
        }
    }
}

@Composable
private fun SavedRoutes(
    routes: List<RouteEntity>
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        routes.forEach {
            OutlinedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        it.from,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .fillMaxWidth(0.4f),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                    Text(
                        it.to,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxWidth(0.4f),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun JourneyInput(
    from: String,
    to: String,
    updateFrom: (String) -> Unit,
    updateTo: (String) -> Unit,
    addRoute: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Where are you going today?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = from,
                onValueChange = updateFrom,
                label = { Text("From") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = to,
                onValueChange = updateTo,
                label = { Text("To") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            )

            Button(
                onClick = addRoute,
                modifier = Modifier.fillMaxWidth(),
                enabled = from.isNotBlank() && to.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null
                )
                Text("Search")
            }
        }
    }
}